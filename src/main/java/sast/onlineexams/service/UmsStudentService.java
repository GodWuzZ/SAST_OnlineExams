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
    int insertStudent(UmsStudent umsStudent);
    int insertStudentMassively(List<UmsStudent> umsStudents);
    int updateStudent(UmsStudent umsStudent);
    int deleteStudent(long id);
    List<UmsStudent> studentList();
}
