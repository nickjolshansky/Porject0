package org.company;

import com.company.dao.DaoFactory;
import com.company.dao.UserDao;
import com.company.entity.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DaoTest {
    @Test
    public void testUser(){
        User user = new User("testName", "testPassword", false);
        UserDao userDao = DaoFactory.getUserDao();
        userDao.insert(user);

        assertEquals("testName", user.getUsername());
    }
}
