package io.yves.ppmtool.services;

import io.yves.ppmtool.domain.Project;
import io.yves.ppmtool.exceptions.ProjectIdException;
import io.yves.ppmtool.respositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {

        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch(Exception e) {
            throw new ProjectIdException("Project ID '" +
                    project.getProjectIdentifier().toUpperCase() + "' already  exists");
        }
    }
}
