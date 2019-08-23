package com.buaa.springBoot.tool;

import java.util.Random;
import java.util.UUID;

public class Token
{
    public static String create()
    {
        return UUID.randomUUID().toString().replace("-", "");
    }
}