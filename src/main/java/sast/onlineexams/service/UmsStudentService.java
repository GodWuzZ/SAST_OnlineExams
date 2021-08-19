package sast.onlineexams.service;



import sast.onlineexams.mbg.model.UmsStudent;

import java.util.List;

/**
 * @author sherman
 * @create 2021-08-11 10:52
 * @description
 */
public interface UmsStudentService {
    String login(String username, String password);
    UmsStudent getStudentByUsername(String username);
    int updateStudentMassively(List<UmsStudent> umsStudents);
    UmsStudent updateStudent(UmsStudent umsStudent);
    void deleteStudent(long id);
    List<UmsStudent> studentList();
}
