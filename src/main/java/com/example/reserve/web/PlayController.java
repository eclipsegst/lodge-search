package com.example.reserve.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.reserve.domain.Gallery;
import com.example.reserve.repository.GalleryRepository;

@Controller

public class PlayController {
	@Autowired
	protected GalleryRepository galleryRepository;
	
	@RequestMapping(value="/play")
	public String play() {
		
		return "test";
	}
	
	@RequestMapping(value="/play/upload", method=RequestMethod.POST)
	public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file){
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = 
						new BufferedOutputStream(new FileOutputStream(new File(name)));
				stream.write(bytes);
				stream.close();
				System.out.println(bytes.toString());
				
				Blob blob = new SerialBlob(bytes);
				long fk = 1;
				String category = "lodge";
				boolean active = false;
	            galleryRepository.save(new Gallery(fk, name, category, active, blob));
      
	            return "You successfully uploaded " + name + "!";
			} catch (Exception e) {
				System.out.println("firs test");
				return "test";
//				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "test";
//			return "You failed to upload " + name + " because the file was empty.";
		}
	}
	
//	@RequestMapping(value = "/image/{image_id}", produces = MediaType.IMAGE_PNG_VALUE)
//    public ResponseEntity<byte[]> getImage(@PathVariable("image_id") Long imageId) throws IOException {
//
//    	Blob blob = galleryRepository.findOne(imageId).getImage();
//
//    	int blobLength = 0;
//		try {
//			blobLength = (int) blob.length();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
//    	byte[] blobAsBytes = null;
//		try {
//			blobAsBytes = blob.getBytes(1, blobLength);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//    	//release the blob and free up memory. (since JDBC 4.0)
//    	try {
//			blob.free();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	
//        byte[] imageContent = blobAsBytes;
//        final HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_PNG);
//        return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
//    }
}
