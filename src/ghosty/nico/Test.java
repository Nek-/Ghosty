package ghosty.nico;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
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
       //System.out.println("File ID: %s" + file.getId());

      return file;
    } catch (IOException e) {
      System.out.println("An error occured: " + e);
      return null;
    }
  }
  
  
  private static ParentReference insertFileIntoFolder(Drive service, String folderId,
	      String fileId) {
	    ParentReference newParent = new ParentReference();
	    newParent.setId(folderId);
	    try {
	      return service.parents().insert(fileId, newParent).execute();
	    } catch (IOException e) {
	      System.out.println("An error occurred: " + e);
	    }
	    return null;
	  }

  
  private static File insertDirectory(Drive service, String title, String description,
	      String parentId, String mimeType, String filename) {
	    // File's metadata.
	  File body = new File();
	    body.setTitle(title);
	    body.setDescription(description);
	    body.setMimeType(mimeType);
	    //body.get(filename);
	    
	    /*if (parentId != null && parentId.length() > 0) {
	        body.setParents(
	            Arrays.asList(new ParentReference().setId(parentId)));
	      }*/
	    // File's content.
	   
	    try {
	     File parent = service.files().insert(body).execute();
	   System.out.println("File ID: %s" + parent.getId());

	      return parent;
	    } catch (IOException e) {
	      System.out.println("An error occured: " + e);
	      return null;
	    }
	  }
  
  
  private static boolean isFileInFolder(Drive service, String folderId,
	      String fileId) throws IOException {
	    try {
	      service.parents().get(fileId, folderId).execute();
	    } catch (HttpResponseException e) {
	      if (e.getStatusCode() == 404) {
	        return false;
	      } else {
	        System.out.println("An error occured: " + e);
	        throw e;
	      }
	    } catch (IOException e) {
	      System.out.println("An error occured: " + e);
	      throw e;
	    }
	    return true;
	  }
  
  /**
   * Print a file's metadata.
   *
   * @param service Drive API service instance.
   * @param fileId ID of the file to print metadata for.
   */
  private static String getDirectory(Drive service, String title, String description,
	      String parentId, String mimeType, String filename) {
	    // File's metadata.
	  File body = new File();
	    body.setTitle(title);
	    body.setDescription(description);
	    body.setMimeType(mimeType);
	    //body.get(filename);
	    
	    /*if (parentId != null && parentId.length() > 0) {
	        body.setParents(
	            Arrays.asList(new ParentReference().setId(parentId)));
	      }*/
	    // File's content.
	   
	    try {
	     File parent = service.files().insert(body).execute();
	   System.out.println("File ID: %s" + parent.getId());

	      return parent.getId();
	    } catch (IOException e) {
	      System.out.println("An error occured: " + e);
	      return null;
	    }
	  }
  
  
  
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
  
 
  
  public static void sendtoDrive(Path path) throws IOException{
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
	   
	    File folder = insertDirectory(service,  path.getFileName().toString(), "description", null ,
				  "application/vnd.google-apps.folder",path.toAbsolutePath().toString()); 
	    insertAll(service,path,folder.getId());
	  
  }
  
  

  
  public static void insertAll(Drive service, Path path, String Id ) throws IOException{
	 
	  DirectoryStream<Path> stream = Files.newDirectoryStream(path);
	  
	  try {
	 
		  Iterator<Path> iterator = stream.iterator();
		 
		  while(iterator.hasNext()) {
			  
			  Path p = iterator.next();
			 
			 if ( Files.isDirectory(p)){
				 	
					File folder = insertDirectory(service,  p.getFileName().toString(), "description", Id ,
					 "application/vnd.google-apps.folder",p.toAbsolutePath().toString());  
				   	insertFileIntoFolder(service, Id, folder.getId());
				   	//insertAll(service, p, folder.getId());
			 }
			 
		else {
				  System.out.println(p.getFileName().toString() + Id);
				 File file = insertFile(service, p.getFileName().toString(), "description", Id,Files.probeContentType(p),p.toAbsolutePath().toString());
				 insertFileIntoFolder(service, Id, file.getId());
			  }
			
		  }
	 
	  } finally {
	 
	  stream.close();
	 
	  }
  }
  
  public static void main(String[] args) throws IOException{
	  Path path = Paths.get("before_coding");
	  sendtoDrive(path);
	  System.out.println("Done");
	  
  }

}
