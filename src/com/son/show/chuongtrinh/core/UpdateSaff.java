package com.son.show.chuongtrinh.core;

import com.son.nhanvien.Staff;
import com.son.nhanvien.StaffFullTime;
import com.son.nhanvien.StaffPartTime;
import com.son.show.chuongtrinh.core.matcher.GmailMatcher;
import com.son.show.chuongtrinh.core.matcher.PhoneNumberMatcher;
import com.son.show.chuongtrinh.fail.AgeFail;
import com.son.show.chuongtrinh.fail.GenderFail;
import com.son.show.chuongtrinh.fail.IdFail;
import com.son.show.file.DocFile;
import com.son.show.file.GhiFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class UpdateSaff {
    private Scanner scanner = new Scanner(System.in);
    private PushAndChangeSaff manager = new PushAndChangeSaff();
    private ArrayList<Staff> list = manager.list;
    private File file = new File("safftemp.txt");
    private PushAndChangeSaff manger = new PushAndChangeSaff();
    private GhiFile<Staff> ghiFile = new GhiFile<>();
    private DocFile<Staff> docFile = new DocFile<>();

    private void check(int check, String a, String b) {
        if (check > 0) {
            System.out.println(a);
        } else {
            System.out.println(b);
        }
    }

    public Staff create(String KieuNv) {
        int id = getId();
        String name = getName();
        int age = getAge();
        String gmail = getGmail();
        String phonenumber = getPhoneNumber();
        String address = getAddress();
        String status = getStatus();
        String gender = getGender();
        double salary = getSalary();
        if (KieuNv.equals("full")) {
            return new StaffFullTime(id, name, gender, age, gmail, phonenumber, address, status, salary);
        } else {
            int hours = getHours();
            return new StaffPartTime(id, name, gender, age, gmail, phonenumber, address, status, salary, hours);
        }
    }

    public void updateNhanVien(PushAndChangeSaff manager) {
        ArrayList<Staff> list = docFile.docFile("qlnv.txt");
        if (list.size() == 0){
            System.out.println("danh s??ch ??ang tr???ng");
        } else {
            System.out.print("Nh???p id c???a nh??n vi??n c???n s???a: ");
            int id = Integer.parseInt(scanner.nextLine());
            getSaffTemp(id);
            int check = -1;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() == id) {
                    if (list.get(i) instanceof StaffFullTime) {
                        list.set(i, create("full"));
                        check = 1;
                        ghiFile.ghiFile("qlnv.txt", list);
                        break;
                    } else {
                        list.set(i, create("part"));
                        check = 1;
                        ghiFile.ghiFile("qlnv.txt", list);
                        break;
                    }
                } else {
                    check = -1;
                }
            }
            check(check, "Update th??nh c??ng", "Kh??ng t??m th???y nh??n vi??n");
        }
    }

    private void getSaffTemp(int id){
        ArrayList<Staff> list = manager.list;
        ArrayList<Staff> listtemp = new ArrayList<>();
        listtemp.add(new Staff(0,"0","0",0,"0","0","0","0",0.0));
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id){
                listtemp.set(0,list.get(i));
                ghiFile.ghiFile("safftemp.txt",listtemp);
            }
        }
    }
    public UpdateSaff(){
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }


    private String getGmail() {
        ArrayList<Staff> listtemp1 = docFile.docFile("safftemp.txt");
        while (true) {
            int check = -1;
            GmailMatcher gmailMatcher = new GmailMatcher();
            System.out.print("Nh???p gmail: ");
            String gmail = scanner.nextLine();
            if (gmailMatcher.validate(gmail) == true) {
                if (list.size() == 0) {
                    return gmail;
                } else if (listtemp1.get(0).getGmail().equals(gmail)) {
                    return gmail;
                }
                else {
                    for (Staff a : list) {
                        if (a.getGmail().equals(gmail)) {
                            check = -1;
                            break;
                        } else {
                            check = 1;
                        }
                    }
                    if (check > 0) {
                        return gmail;
                    } else {
                        System.out.println("gmail b??? tr??ng");
                    }
                }
            } else {
                System.out.println("Nh???p l???i");
            }
        }
    }

    private String getPhoneNumber() {
        ArrayList<Staff> listtemp1 = docFile.docFile("safftemp.txt");
        while (true) {
            int check = -1;
            PhoneNumberMatcher phoneNumberMatcher = new PhoneNumberMatcher();
            System.out.print("Nh???p s??? ??i???n tho???i: ");
            String phonenumber = scanner.nextLine();
            if (phoneNumberMatcher.validate(phonenumber) == true) {
                if (list.size() == 0) {
                    return phonenumber;
                } else if (listtemp1.get(0).getPhonenumber().equals(phonenumber)){
                    return phonenumber;
                }
                else {
                    for (Staff a : list) {
                        if (a.getPhonenumber().equals(phonenumber)) {
                            check = -1;
                        } else {
                            check = 1;
                        }
                    }
                    if (check > 0) {
                        return phonenumber;
                    } else {
                        System.out.println("s??? ??i???n tho???i b??? tr??ng");
                    }

                }
            } else {
                System.out.println("Nh???p l???i");
            }
        }
    }

    private String getName() {
        System.out.print("Nh???p t??n nh??n vi??n: ");
        return scanner.nextLine();
    }

    private int getAge() {
        while (true) {
            try {
                System.out.print("nh???p tu???i c???a nh??n vi??n: ");
                int age = Integer.parseInt(scanner.nextLine());
                if (age < 18) {
                    throw new AgeFail();
                }
                return age;
            } catch (AgeFail e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("sai ?????nh d???ng");
            }
        }
    }

    private String getGender() {
        while (true) {
            try {
                System.out.print("Nh???p gi???i t??nh(Nam\\N???): ");
                String gender = scanner.nextLine();
                if (gender.matches("nam") || gender.matches("nu") || gender.matches("n???")) {
                    if (gender.equals("nam")) {
                        return "Nam";
                    } else {
                        return "N???";
                    }
                } else {
                    throw new GenderFail();
                }
            } catch (GenderFail e) {
                e.getMessage();
            }
        }
    }

    private String getAddress() {
        System.out.print("Nh???p ?????a ch??? c???a nh??n vi??n: ");
        return scanner.nextLine();
    }

    private String getStatus() {
        while (true) {
            System.out.println("   1. ??ang ??i l??m");
            System.out.println("   2. ??ang ngh???");
            System.out.println("   3. quay l???i");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    return "??ang ??i l??m";
                case 2:
                    return "??ang ngh???";
                case 3:
                    System.exit(0);
            }
        }
    }

    private double getSalary() {
        while (true) {
            try {
                System.out.print("Nh???p ti???n l????ng hi???n t???i c???a nh??n vi??n: ");
                return Double.parseDouble(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("sai d???nh d???ng");
            }
        }
    }

    private int getHours() {
        while (true) {
            try {
                System.out.print("Nh???p s??? gi??? l??m vi???c: ");
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("sai ?????nh d???ng");
            }
        }
    }

    private int getId() {
        ArrayList<Staff> listtemp1 = docFile.docFile("safftemp.txt");
        while (true) {
            try {
                System.out.print("Nh???p id c???a nh??n vi??n(Nh???p s???): ");
                int id = Integer.parseInt(scanner.nextLine());
                if (list.isEmpty()) {
                    return id;
                } else if (id == listtemp1.get(0).getId()){
                    return id;
                }
                else {
                    for (Staff a : list) {
                        if (a.getId() == id) {
                            throw new IdFail();
                        }
                    }
                    return id;
                }
            } catch (IdFail e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Sai ?????nh d???ng");
            }
        }
    }

}
