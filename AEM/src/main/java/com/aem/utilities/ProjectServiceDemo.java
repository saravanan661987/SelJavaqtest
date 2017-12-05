package com.aem.utilities;

import org.qas.api.ClientConfiguration;
import org.qas.qtest.api.auth.BasicQTestCredentials;
import org.qas.qtest.api.auth.QTestCredentials;
import org.qas.qtest.api.services.project.ProjectService;
import org.qas.qtest.api.services.project.ProjectServiceClient;
import org.qas.qtest.api.services.project.model.ListProjectRequest;
import org.qas.qtest.api.services.project.model.Project;

import java.util.List;

/**
 * Main
 *
 * @author Dzung Nguyen (dungvnguyen@qasymphony.com)
 * @version $Id Main 2014-05-06 20:16:30z dungvnguyen $
 * @since 1.0
 */
public class ProjectServiceDemo {
  /**
   * The main entry point.
   */
  public static void main(String[] args) throws Exception {
    ClientConfiguration config = SdkSampleUtils.createConfiguration();

    // create credentials.
    QTestCredentials credentials = SdkSampleUtils.getCredentials();

    // list project.
    ProjectService projectService = new ProjectServiceClient(credentials, config);
    List<Project> projects = projectService.listProject(
       new ListProjectRequest()
          .withAssignOnly(true)
    );
    System.out.println("Listing all project on your site: " + SdkSampleUtils.getUrl());
    if (projects == null || projects.isEmpty()) {
      System.out.println("No project found.");
    } else {
      for (Project project : projects) {
        System.out.println("Project:");
        System.out.println(project);
      }
    }
  }
}
