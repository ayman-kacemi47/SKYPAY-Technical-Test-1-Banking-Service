package net.kacemi.skypaybanktest1.skypaybanktest1.entites;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data @AllArgsConstructor
@NoArgsConstructor
public class Client {
private String id;
private String fullName;
private String password;
private List<Account> accounts;

}
