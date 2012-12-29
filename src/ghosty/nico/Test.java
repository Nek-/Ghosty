package ghosty.nico;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.Drive.*;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.*;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.nio.file.Files;
import java.util.Arrays;
import java.util.Iterator;


public class Test {

  // ...

  /**
   * Insert new file.
   *
   * @param service Drive API service instance.
   * @param title Title of the file to insert, including the extension.
   * @param description Description of the file to insert.
   * @param parentId Optional parent folder's ID.
   * @param mimeType MIME type of the file to insert.
   * @param filename Filename of the file to insert.
   * @return Inserted file metadata if successful, {@code null} otherwise.
   */
  private static File insertFile(Drive service, String title, String description,
      String parentId, String mimeType, String filename) {
    // File's metadata.
    File body = new File();
    body.setTitle(title);
    body.setDescription(description);
    body.setMimeType(mimeType);

    // Set the parent folder.
    if (parentId != null && parentId.length() > 0) {
      body.setParents(
          Arrays.asList(new ParentReference().setId(parentId)));
    }

    // File's content.
    Path fileContent = Paths.get(filename);
    
    FileContent mediaContent = new FileContent(mimeType, fileContent.toFile());
    try {
      File file = service.files().insert(body, mediaContent).execute();

      // Uncomment the following line to print the File ID.
      // System.out.println("File ID: %s" + file.getId());

      return file;
    } catch (IOException e) {
      System.out.println("An error occured: " + e);
      return null;
    }
  }
  
  
  private static File insertDirectory(Drive service, String title, String description,
	      String parentId, String mimeType, String filename) {
	    // File's metadata.
	  File body = new File();
	    body.setTitle(title);
	    body.setDescription(description);
	    body.setMimeType(mimeType);
	    body.get(filename);
	    // File's content.
	   
	    try {
	     File parent = service.files().insert(body).execute();
	      // Uncomment the following line to print the File ID.
	      // System.out.println("File ID: %s" + file.getId());

	      return parent;
	    } catch (IOException e) {
	      System.out.println("An error occured: " + e);
	      return null;
	    }
	  }
  
  
  /**
   * Print a file's metadata.
   *
   * @param service Drive API service instance.
   * @param fileId ID of the file to print metadata for.
   */
  private static void printFile(Drive service, String fileId) {

    try {
      File file = service.files().get(fileId).execute();

      System.out.println("Title: " + file.getTitle());
      System.out.println("Description: " + file.getDescription());
      System.out.println("MIME type: " + file.getMimeType());
    } catch (IOException e) {
      System.out.println("An error occured: " + e);
    }
  }

  /**
   * Download a file's content.
   *
   * @param service Drive API service instance.
   * @param file Drive File instance.
   * @return InputStream containing the file's content if successful,
   *         {@code null} otherwise.
   */
  private static InputStream downloadFile(Drive service, File file) {
    if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
      try {
        HttpResponse resp =
            service.getRequestFactory().buildGetRequest(new GenericUrl(file.getDownloadUrl()))
                .execute();
        return resp.getContent();
      } catch (IOException e) {
        // An error occurred.
        e.printStackTrace();
        return null;
      }
    } else {
      // The file doesn't have any content stored on Drive.
      return null;
    }
  }
  
 
  
  public static void main(String[] args) throws IOException{
	    String CLIENT_ID = "992264517523.apps.googleusercontent.com";
	    String CLIENT_SECRET = "mKIP4ka2grpm2t67oQQqa7GZ";
	    String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
	    
	    HttpTransport httpTransport = new NetHttpTransport();
	    JsonFactory jsonFactory = new JacksonFactory();
	   
	    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	        httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
	        .setAccessType("online")
	        .setApprovalPrompt("auto").build();
	    
	    String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
	    System.out.println("Please open the following URL in your browser then type the authorization code:");
	    System.out.println("  " + url);
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    String code = br.readLine();
	    
	    GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
	    GoogleCredential credential = new GoogleCredential().setFromTokenResponse(response);
	    
	    //Create a new authorized API client
	    Drive service = new Drive.Builder(httpTransport, jsonFactory, credential).build();
	  
	  
	  Path path = Paths.get("before_coding");
	  
	  DirectoryStream<Path> stream = Files.newDirectoryStream(path);
	  
	  try {
	 
		  Iterator<Path> iterator = stream.iterator();
		 
		  while(iterator.hasNext()) {
			  
			  Path p = iterator.next();
			 
			  if ( Files.isDirectory(p)){
				   System.out.println(p.getFileName().toString());
				   insertDirectory(service,  p.getFileName().toString(), "description", null,  "application/vnd.google-apps.folder", p.getFileName().toString());
			  }
			  else 
				  insertFile(service, "title", "description", null,"text/plain", p.getFileName().toString());
		  }
	 
	  } finally {
	 
	  stream.close();
	 
	  }
	  
  }
}
