package io.yves.ppmtool.services;

import io.yves.ppmtool.domain.Backlog;
import io.yves.ppmtool.domain.Project;
import io.yves.ppmtool.domain.ProjectTask;
import io.yves.ppmtool.exceptions.ProjectNotFoundException;
import io.yves.ppmtool.respositories.BacklogRepository;
import io.yves.ppmtool.respositories.ProjectRepository;
import io.yves.ppmtool.respositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
        try {
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            projectTask.setBacklog(backlog);
            Integer backlogSequence = backlog.getPTSequence();
            backlogSequence++;

            backlog.setPTSequence(backlogSequence);

            projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            if(projectTask.getPriority() == null) {
                projectTask.setPriority(3);
            }

            if(projectTask.getStatus() == "" || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);
        } catch(Exception e) {
            throw new ProjectNotFoundException("Project not found");
        }
    }

    public Iterable<ProjectTask> findBacklogById(String id) {
        Project project = projectRepository.findByProjectIdentifier(id);

        if(project == null) {
            throw new ProjectNotFoundException("Project with id " + id + " doesn't exist");
        }

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if(backlog == null) {
            throw new ProjectNotFoundException("Projet with id '" + backlog_id + "' not found");
        }

        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if(projectTask == null) {
            throw new ProjectNotFoundException("Project task '" + pt_id + "' not found");
        }

        if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
            throw new ProjectNotFoundException("Projet task '" + pt_id + "' does not exist in project: " + backlog_id);
        }

        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id) {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);

        projectTask = updatedTask;

        return projectTaskRepository.save(projectTask);
    }
}
