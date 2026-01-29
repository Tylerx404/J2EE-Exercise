import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        List<Book> listBook = new ArrayList<>();
        Scanner x = new Scanner(System.in);
        String msg = """
                Chương trình quản lý sách
                    1. Thêm 1 cuốn sách
                    2. Xoá 1 cuốn sách
                    3. Thay đổi sách
                    4. Xuất thông tin
                    5. Tìm sách Lập trình
                    6. Lấy sách tối đa theo giá
                    7. Tìm kiếm theo tác giả
                    0. Thoát
                    Chọn chức năng:""";
        int chon = 0;
        do {
            System.out.print(msg);
            chon = x.nextInt();
            switch (chon) {
                case 1 -> {
                    Book newBook = new Book();
                    newBook.input();
                    listBook.add(newBook);
                }

                case 2 -> {
                    System.out.print("Nhập vào mã sách cần xoá: ");
                    int bookid = x.nextInt();
                    // Kiểm tra mã sách
                    Book find = listBook.stream().filter(p -> p.getId() == bookid).findFirst().orElseThrow();
                    listBook.remove(find);
                    System.out.println("Đã xoá thành công!");
                }

                case 3 -> {
                    System.out.print("Nhập vào mã sách cần điều chỉnh: ");
                    int bookid = x.nextInt();
                    Book find = listBook.stream().filter(p -> p.getId() == bookid).findFirst().orElseThrow();
                    System.out.println("Nhập thông tin mới cho sách:");
                    find.input();
                    System.out.println("Đã cập nhật thành công!");
                }

                case 4 -> {
                    System.out.println("\n Xuất thông tin danh sách ");
                    listBook.forEach(p -> p.output());
                }

                case 5 -> {
                    List<Book> list5 = listBook.stream()
                            .filter(u -> u.getTitle().toLowerCase().contains("lập trình"))
                            .toList();
                    list5.forEach(Book::output);
                }

                case 6 -> {
                    if (listBook.isEmpty()) {
                        System.out.println("Danh sách sách trống!");
                    } else {
                        Book maxPriceBook = listBook.stream()
                                .max((b1, b2) -> Long.compare(b1.getPrice(), b2.getPrice()))
                                .orElseThrow();
                        System.out.println("Sách có giá cao nhất:");
                        maxPriceBook.output();
                    }
                }

                case 7 -> {
                    System.out.print("Nhập vào tên tác giả cần tìm: ");
                    x.nextLine(); // Clear buffer
                    String authorName = x.nextLine();
                    List<Book> list7 = listBook.stream()
                            .filter(u -> u.getAuthor().toLowerCase().contains(authorName.toLowerCase()))
                            .toList();
                    if (list7.isEmpty()) {
                        System.out.println("Không tìm thấy sách của tác giả: " + authorName);
                    } else {
                        System.out.println("Danh sách sách của tác giả '" + authorName + "':");
                        list7.forEach(Book::output);
                    }
                }

                case 0 -> {
                    System.out.println("Tạm biệt!");
                }

                default -> {
                    System.out.println("Chức năng không hợp lệ! Vui lòng chọn lại.");
                }
            }
        } while (chon != 0);
    }
}
