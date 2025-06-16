package lk.earth.earthuniversity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;

@Entity
public class Employee {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "code")
    private String code;
    @Basic
    @Column(name = "photo")
    private byte[] photo;
    @Basic
    @Column(name = "dob")
    private Date dob;
    @Basic
    @Column(name = "nic")
    private String nic;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "mobile")
    private String mobile;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "doassignment")
    private String doassignment;
    @Basic
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id", nullable = false)
    private Gender gender;
    @ManyToOne
    @JoinColumn(name = "emptype_id", referencedColumnName = "id", nullable = false)
    private Emptype emptype;
    @ManyToOne
    @JoinColumn(name = "designation_id", referencedColumnName = "id", nullable = false)
    private Designation designation;
    @ManyToOne
    @JoinColumn(name = "empstatus_id", referencedColumnName = "id", nullable = false)
    private Empstatus empstatus;
    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    private Collection<User> users;

    public Employee(){}
    public Employee(Integer id,String name){
        this.id=id;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDoassignment() {
        return doassignment;
    }

    public void setDoassignment(String doassignment) {
        this.doassignment = doassignment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (id != employee.id) return false;
        if (name != null ? !name.equals(employee.name) : employee.name != null) return false;
        if (code != null ? !code.equals(employee.code) : employee.code != null) return false;
        if (!Arrays.equals(photo, employee.photo)) return false;
        if (dob != null ? !dob.equals(employee.dob) : employee.dob != null) return false;
        if (nic != null ? !nic.equals(employee.nic) : employee.nic != null) return false;
        if (address != null ? !address.equals(employee.address) : employee.address != null) return false;
        if (mobile != null ? !mobile.equals(employee.mobile) : employee.mobile != null) return false;
        if (email != null ? !email.equals(employee.email) : employee.email != null) return false;
        if (doassignment != null ? !doassignment.equals(employee.doassignment) : employee.doassignment != null)
            return false;
        if (description != null ? !description.equals(employee.description) : employee.description != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(photo);
        result = 31 * result + (dob != null ? dob.hashCode() : 0);
        result = 31 * result + (nic != null ? nic.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (doassignment != null ? doassignment.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Emptype getEmptype() {
        return emptype;
    }

    public void setEmptype(Emptype emptype) {
        this.emptype = emptype;
    }

    public Designation getDesignation() {
        return designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public Empstatus getEmpstatus() {
        return empstatus;
    }

    public void setEmpstatus(Empstatus empstatus) {
        this.empstatus = empstatus;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}
