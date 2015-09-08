package com.example.reserve.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.annotation.Nonnull;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.reserve.domain.Gallery;
import com.example.reserve.service.GalleryRepository;

@Controller
public class ImageController {
	@Autowired
	protected GalleryRepository galleryRepository;
	
	protected long fk;
	protected String category;
	
	@RequestMapping(value="/image")
	public String image(
			@Nonnull @RequestParam(value = "fk", required = true) final long fk,
			@Nonnull @RequestParam(value = "category", required = true) final String category,
			Model model
			) {
		
		model.addAttribute("fk", fk);
		model.addAttribute("category", category);
		this.fk = fk;
		this.category = category;
		return "image-new";
	}
	
	/*
	 * Uploading single image
	 */
	@RequestMapping(value="/image/upload", method=RequestMethod.POST)
	public @ResponseBody String imageUpload(
			@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file){
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				// It will save image in file directory using following lines.
				
//				BufferedOutputStream stream = 
//						new BufferedOutputStream(new FileOutputStream(new File(name)));
//				stream.write(bytes);
//				stream.close();
				
				// Save image to database
				Blob blob = new SerialBlob(bytes);
				// We use active to determine if the image is default one.
				boolean active = false;
	            galleryRepository.save(new Gallery(fk, name, category, active, blob));
      
	            return "You successfully uploaded " + name + "!";
	            
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name + " because the file was empty.";
		}
	}
	
	
	/*
	 * Display single image
	 */
	@RequestMapping(value = "/image/view", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(
			@Nonnull @RequestParam(value = "id", required = true) final long id
			) throws IOException {
			 
    	Blob blob = galleryRepository.findOne(id).getImage();

    	int blobLength = 0;
		try {
			blobLength = (int) blob.length();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    	byte[] blobAsBytes = null;
		try {
			blobAsBytes = blob.getBytes(1, blobLength);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	//release the blob and free up memory. (since JDBC 4.0)
    	try {
			blob.free();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        byte[] imageContent = blobAsBytes;
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
    }
}
