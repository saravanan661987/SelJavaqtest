package com.aem.utilities;

import org.qas.api.ClientConfiguration;
import org.qas.qtest.api.auth.QTestCredentials;
import org.qas.qtest.api.services.attachment.model.Attachment;
import org.qas.qtest.api.services.design.model.TestStep;
import org.qas.qtest.api.services.execution.TestExecutionService;
import org.qas.qtest.api.services.execution.TestExecutionServiceClient;
import org.qas.qtest.api.services.execution.model.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Main
 *
 * @author Dzung Nguyen (dungvnguyen@qasymphony.com)
 * @version $Id Main 2014-05-06 20:16:30z dungvnguyen $
 * @since 1.0
 */
public class TestExecutionDemo {

  /**
   * The main entry point.
   */
  public static void main(String[] args) throws Exception {
    ClientConfiguration config = SdkSampleUtils.createConfiguration();
    // create credentials.
    QTestCredentials credentials = SdkSampleUtils.getCredentials();

    // get project id.
    String project = SdkSampleUtils.getProjectId();
    Long projectId = Long.parseLong(project);

    // create automation test log and list all test runs.
    TestExecutionService testExecutionService = new TestExecutionServiceClient(credentials, config);

    // view all test run.
    viewAllTestRunOfProject(testExecutionService, projectId);
//    submitAutomationTestLog(testExecutionService, projectId);
    submitAutomationTestLogWithTestSteps(testExecutionService, projectId, false);
    submitAutomationTestLogWithTestSteps(testExecutionService, projectId, true);
    submitAutomationTestLog(testExecutionService, projectId);

    submitManualTestLogWithoutTestStepLog(testExecutionService, projectId);

    submitManualTestLogWithTestStepLog(testExecutionService, projectId);
    getLastLog(testExecutionService, projectId);
  }

  // view all test-runs in project.
  private static void viewAllTestRunOfProject(TestExecutionService executionService, Long projectId) {
    System.out.println("BEGIN list all test-runs in project ...");
    try {
      List<TestRun> testRuns = executionService.listTestRun(new ListTestRunRequest().withProjectId(projectId));
      System.out.println("Listing all test run in project: " + projectId + ". We only display first 10 of test-runs");
      if (testRuns == null || testRuns.isEmpty()) {
        System.out.println("No test run in project");
      } else {
        int count = 0;
        for (TestRun testRun : testRuns) {
          System.out.println("TestRun: ===========================");
          System.out.println(testRun);
          count++;
          if (count == 10) break;
        }
      }
    } finally {
      System.out.println("END list all test-runs in project ...");
    }
  }

  // submit test log.
  private static void submitManualTestLogWithoutTestStepLog(TestExecutionService executionService, Long projectId) {
    System.out.println("BEGIN submit manual test log ....");

    try {
      // fetch the list of test execution.
      List<ExecutionStatus> executionStatuses = executionService.listExecutionStatus(
        new ListExecutionStatusRequest().withProjectId(projectId)
      );

      // fetch the list of test run.
      List<TestRun> testRuns = executionService.listTestRun(new ListTestRunRequest().withProjectId(projectId));
      if (testRuns.isEmpty()) return;

      // fetch the test-run details with test-case and test-step
      TestRun testRun = executionService.getTestRun(
        new GetTestRunRequest()
          .withProjectId(projectId)
          .withTestRunId(testRuns.get(0).getId())
          .withExpandTestCase(true)
      );

      TestLog testLog = new TestLog().withTestCase(testRun.getTestCase())
        .withTestCaseVersionId(testRun.getTestCaseVersionId())
        .withExecutionStartDate(new Date())
        .withExecutionEndDate(new Date())
        .withNote("Submit manual test-run without test-step.")
        .withStatus(executionStatuses.get(0));
      // submit test log.
      TestLog testLogResult = executionService.submitTestLog(
        new SubmitTestLogRequest().withProjectId(projectId)
          .withTestRunId(testRun.getId())
          .withTestLog(testLog)
      );

      System.out.println("TEST-LOG result ===================");
      System.out.println(testLogResult);
    } finally {
      System.out.println("END submit manual test log ....");
    }
  }

  // submit test log.
  private static void submitManualTestLogWithTestStepLog(TestExecutionService executionService,
                                                         Long projectId) {
    System.out.println("BEGIN submit manual test log ....");

    try {
      // fetch the list of test execution.
      List<ExecutionStatus> executionStatuses = executionService.listExecutionStatus(
        new ListExecutionStatusRequest().withProjectId(projectId)
      );

      // fetch the list of test run.
      List<TestRun> testRuns = executionService.listTestRun(new ListTestRunRequest().withProjectId(projectId));
      if (testRuns.isEmpty()) return;

      // fetch the test-run details with test-case and test-step
      TestRun testRun = executionService.getTestRun(
        new GetTestRunRequest()
          .withProjectId(projectId)
          .withTestRunId(testRuns.get(0).getId())
          .withExpandTestStep(true)
      );

      // build test step log.
      List<TestStepLog> testStepLogs = new ArrayList<TestStepLog>();
      int count = 0;
      List<TestStep> testSteps = testRun.getTestCase().getTestSteps();
      for (int index = 0; index < testSteps.size(); index++) {
        TestStep testStep = testSteps.get(testSteps.size() - (index + 1));
        testStepLogs.add(
          new TestStepLog()
            .withTestStepId(testStep.getId())
            .withStatus(executionStatuses.get(count % 2))
            .withActualResult("Test step: {" + testStep.getId() + "} - actual result")
        );
        count++;
      }
      // build manual test log with test step log.
      TestLog testLog = new TestLog()
        .withTestCase(testRun.getTestCase())
        .withTestCaseVersionId(testRun.getTestCaseVersionId())
        .withExecutionStartDate(new Date())
        .withExecutionEndDate(new Date())
        .withNote("Submit manual test-run with test-step log")
        .withStatus(executionStatuses.get(0))
        .withTestStepLogs(testStepLogs);

      System.out.println("Test log: " + testLog);
      try {
        System.out.println("Test log JSON: " + testLog.toJson());
      } catch (Exception ex) {
      }

      // submit test log.
      TestLog testLogResult = executionService.submitTestLog(
        new SubmitTestLogRequest().withProjectId(projectId)
          .withTestRunId(testRun.getId())
          .withTestLog(testLog)
      );

      // print information.
      System.out.println("TEST-LOG result ===================");
      System.out.println(testLogResult);
    } finally {
      System.out.println("END submit manual test log ....");
    }
  }

  // submit automation test log.
  private static void submitAutomationTestLog(TestExecutionService executionService, Long projectId) {
    System.out.println("BEGIN submit automation test log ...");
    try {
      TestLog testLog = executionService.submitAutomationTestLog(
        new AutomationTestLogRequest()
          .withProjectId(projectId)
          .withTestRunId(0L)
          .withAutomationTestLog(
            new AutomationTestLog()
              .withExecutionStartDate(new Date())
              .withExecutionEndDate(new Date())
              .withName("AutomationTestLog")
              .withAutomationContent("<test>AutomationContent</test>")
              .withStatus("PASS")
              .withSystemName("TestNG")
          )
      );

      System.out.println("TESTLOG result ========================");
      System.out.println(testLog);
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      System.out.println("END submit automation test log ...");
    }
  }

  // submit automation test log.
  private static void submitAutomationTestLogWithTestSteps(TestExecutionService executionService,
                                                           Long projectId, boolean suitePerDay) {
    System.out.println("BEGIN submit automation test log with test steps...");
    try {
      // create input stream.
      // create list of test step log.
      List<AutomationTestStepLog> testStepLogs = new ArrayList<AutomationTestStepLog>();
      testStepLogs.add(
        new AutomationTestStepLog()
          .withDescription("test step 1")
          .withExpected("result 1")
          .withActualResult("result 5")
          .withStatus("FAIL")
      );

      testStepLogs.add(
        new AutomationTestStepLog()
          .withDescription("test step 2")
          .withExpected("result 2")
          .withAttachments(Collections.singletonList(
            new Attachment()
              .withName("test_step_2.txt")
              .withContentType("text/plain")
              .withData(new ByteArrayInputStream("Test Step step attachment content".getBytes()))
          ))
          .withStatus("PASS")
      );

      // create list of test log.
      TestLog testLog = executionService.submitAutomationTestLog(
        new AutomationTestLogRequest()
          .withProjectId(projectId)
          .withTestRunId(0L)
          .withSuitePerDay(suitePerDay)
          .withAutomationTestLog(
            new AutomationTestLog()
              .withExecutionStartDate(new Date())
              .withExecutionEndDate(new Date())
              .withName("AutomationTestLog")
              .withAutomationContent("<test>AutomationContent</test>")
              .withStatus("PASS")
              .withSystemName("TestNG")
              .withTestStepLogs(testStepLogs)
          )
      );

      System.out.println("TESTLOG result ========================");
      System.out.println(testLog);
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      System.out.println("END submit automation test log with test steps ...");
    }
  }

  // get last log of the test run.
  private static void getLastLog(TestExecutionService executionService, Long projectId) {
    System.out.println("BEGIN submit automation test log ...");
    try {
      // fetch the list of test run.
      List<TestRun> testRuns = executionService.listTestRun(new ListTestRunRequest().withProjectId(projectId));
      if (testRuns.isEmpty()) return;

      // get the last log of test run.
      TestLog testLog = executionService.getLastLog(
        new GetLastLogRequest()
          .withProjectId(projectId)
          .withTestRunId(testRuns.get(0).getId())
      );
      System.out.println("TEST LOG ========================================");
      System.out.println(testLog);

      // fetch test log with test step log.
      testLog = executionService.getLastLog(
        new GetLastLogRequest()
          .withProjectId(projectId)
          .withTestRunId(testRuns.get(0).getId())
          .withExpandTestCase(true)
      );
      System.out.println("TEST LOG WITH TEST CASE =====================");
      System.out.println(testLog);

      // fetch test log with test step log.
      testLog = executionService.getLastLog(
        new GetLastLogRequest()
          .withProjectId(projectId)
          .withTestRunId(testRuns.get(0).getId())
          .withExpandTestCase(true)
          .withExpandTestStep(true)
      );
      System.out.println("TEST LOG WITH TESTCASE, TEST STEP LOG AND TEST STEP =======");
      System.out.println(testLog);
    } finally {
      System.out.println("END submit automation test log ...");
    }
  }

}
