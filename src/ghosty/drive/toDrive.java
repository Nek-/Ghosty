package ghosty.drive;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.*;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;


public class toDrive {


  private static File insertFile(Drive service, String title, String description,
      String parentId, String mimeType, String filename/*,String MD5 */) {
    // File's metadata.
    File body = new File();
    body.setTitle(title);
    body.setDescription(description);
    body.setMimeType(mimeType);
    //body.setMd5Checksum(MD5);

  
    if (parentId != null && parentId.length() > 0) {
      body.setParents(
          Arrays.asList(new ParentReference().setId(parentId)));
    }

  

    Path fileContent = Paths.get(filename);
    FileContent mediaContent = new FileContent(mimeType, fileContent.toFile());
    try {
      File file = service.files().insert(body, mediaContent).execute();

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
	      String parentId, String mimeType, String filename/*,String MD5 */) {
	  File body = new File();
	    body.setTitle(title);
	    body.setDescription(description);
	    body.setMimeType(mimeType);
	    //body.setMd5Checksum(MD5);

	    try {
	     File parent = service.files().insert(body).execute();
	 

	      return parent;
	    } catch (IOException e) {
	      System.out.println("An error occured: " + e);
	      return null;
	    }
	  }
  
  

  
 
  
  
  
  private static File updateFile(Drive service, String fileId, String newTitle,
	      String newDescription, String newMimeType, String newFilename, boolean newRevision) {
	    try {
	     
	      File file = service.files().get(fileId).execute();

	      
	      file.setTitle(newTitle);
	      file.setDescription(newDescription);
	      file.setMimeType(newMimeType);

	      // File's new content.
	      Path fileContent = Paths.get(newFilename);
	      FileContent mediaContent = new FileContent(newMimeType, fileContent.toFile());

	      
	      File updatedFile = service.files().update(fileId, file, mediaContent).execute();

	      return updatedFile;
	    } catch (IOException e) {
	      System.out.println("An error occurred: " + e);
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
				   	insertAll(service, p, folder.getId());
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
  
}
