package com.example.reserve.service;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reserve.domain.Gallery;

@Service
public class GalleryService {
	private final GalleryRepository galleryRepository;
	
	@Autowired
	public GalleryService(
			@Nonnull final GalleryRepository galleryRepository
			) {
		this.galleryRepository = galleryRepository;
	}
	
	public final List<Gallery> findAll() {
		final List<Gallery> gallerys = (List<Gallery>) galleryRepository.findAll();
		return gallerys;
	}
	
	public final Gallery findOne(Long galleryId) {
		final Gallery gallery = galleryRepository.findOne(galleryId);
		return gallery;
	}
	
	public final List<Gallery> findByFkByCategory(Long fk, String category) {
		final List<Gallery> gallerys = (List<Gallery>) galleryRepository.findByFkEqualsAndCategoryEquals(fk, category);
		return gallerys;
	}
	
	public final void save(Gallery gallery) {
		this.galleryRepository.save(gallery);
	}
	
	public final void deleteGallery(Long galleryId) {
		final Gallery gallery = galleryRepository.findOne(galleryId);
		galleryRepository.delete(gallery);
	}
}
