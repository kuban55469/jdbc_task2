package java8.dao;

import java8.model.Job;

import java.util.List;

/**
 * @author :ЛОКИ Kelsivbekov
 * @created 20.01.2023
 */
public interface JobDao {
    void createJobTable();
    void addJob(Job job);
    Job getJobById(Long jobId);
    List<Job> sortByExperience(String ascOrDesc);
    Job getJobByEmployeeId(Long employeeId);

    void deleteDescriptionColumn();
}
