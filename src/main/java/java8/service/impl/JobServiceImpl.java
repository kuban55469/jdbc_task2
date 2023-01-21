package java8.service.impl;

import java8.dao.EmployeeDao;
import java8.dao.impl.EmployeeDaoImpl;
import java8.dao.impl.JobDaoImpl;
import java8.model.Job;
import java8.service.JobService;

import java.util.List;

/**
 * @author :ЛОКИ Kelsivbekov
 * @created 20.01.2023
 */
public class JobServiceImpl implements JobService {
    JobDaoImpl jobDao = new JobDaoImpl();
    @Override
    public void createJobTable() {
        jobDao.createJobTable();
    }

    @Override
    public void addJob(Job job) {
        jobDao.addJob(job);
    }

    @Override
    public Job getJobById(Long jobId) {
        return jobDao.getJobById(jobId);
    }

    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
        return jobDao.sortByExperience(ascOrDesc);
    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        return jobDao.getJobByEmployeeId(employeeId);
    }


    @Override
    public void deleteDescriptionColumn() {
        jobDao.deleteDescriptionColumn();
    }
}
