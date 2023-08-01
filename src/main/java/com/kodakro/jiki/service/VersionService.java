package com.kodakro.jiki.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodakro.jiki.model.Version;
import com.kodakro.jiki.repository.VersionRepository;

@Service
public class VersionService {
	
	@Autowired
	VersionRepository versionRepository;
	
	public List<Version> findByProject(Long projectId){
		return versionRepository.findByProject(projectId);
	}
	
	public Version create(Version version) {
		Optional<Version> dbVersion = versionRepository.exists(version.getId());
		if (dbVersion.isPresent()) {
			return null;
		}
		return versionRepository.create(version);
	}
}
