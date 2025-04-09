package com.capstone.RFID_padlock.Entity.Service;

import com.capstone.RFID_padlock.Entity.KeyCard;
import com.capstone.RFID_padlock.Entity.Repository.StudentRepository;
import com.capstone.RFID_padlock.Entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService implements ServiceInterface<Student> {

    private final StudentRepository studentRepository;
    private KeyCardService keyCardService;

    @Autowired
    public StudentService(StudentRepository studentRepository, KeyCardService keyCardService) {
        this.studentRepository = studentRepository;
        this.keyCardService = keyCardService;
    }

    @Override
    public Student addEntity(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllEntities() {
        return studentRepository.findAll();
    }

    @Override
    public Student  getEntity(Long id) {
        if (studentRepository.findById(id).isPresent()) {
            return studentRepository.findById(id).get();
        } else {
            return null;
        }
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void delete(Student student) {
        studentRepository.delete(student);
    }

    @Override
    public void delete(Long id) {
        studentRepository.delete(getEntity(id));
    }

    public void assignKeyCard(Long studentId, Long keyCardId){
        //defining entities from the id params.
        Student student = getEntity(studentId);
        KeyCard keyCard = keyCardService.getEntity(keyCardId);

        //checks if the key card has a student already defined
        if (keyCard.getStudentId() != null) {
            //unlinks the student form the key card
            Student previousStudent = getEntity(keyCard.getStudentId());
            previousStudent.setKeyCardId(null);
            save(previousStudent);
        }

        //links the student and the key card
        student.setKeyCardId(keyCardId);
        keyCard.setStudentId(studentId);
        save(student);
        keyCardService.save(keyCard);
    }

    public Student synchronize(Long studentId) {
        //define student from id
        Student student = getEntity(studentId);

        //if the student has a key card id declared it will assign the key card to the student
        if (student.getKeyCardId() != null) {
            //see assign method for more detail
            assignKeyCard(student.getId(), student.getKeyCardId());
        }

        //saves
        return save(student);
    }

    public Student synchronize(Student student) {
        //checks if student exists
        if (student.getId() == null || getEntity(student.getId()) == null) {
            return null;
        }

        //if the student has a key card id declared it will assign the key card to the student
        if (student.getKeyCardId() != null) {
            //see assign method for more detail
            assignKeyCard(student.getId(), student.getKeyCardId());
        }

        //saves
        return save(student);
    }

    public Student synchronizeAdd(Student student) {
        //adds new student entity to the database based on the student param
        Student newStudent = addEntity(student);

        //if the student has a key card id declared it will assign the key card to the student
        //this function need the student in the database for assignment, which is why it runs after creation of the new student
        if (newStudent.getKeyCardId() != null) {
            assignKeyCard(newStudent.getId(), newStudent.getKeyCardId());
        }

        //saves the updates, if any, to the database
        return save(newStudent);
    }

    public List<Student> getStudentsWhereKeyCardIdIsNotNull() {
        return studentRepository.findByKeyCardIdIsNotNull();
    }



}
