package com.automation.utils.decoding;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class UrlDecoderUtil
{
    private static String decoded = "";

    public static String decodePath(String pathToDecode)
    {
        try
        {
            decoded = URLDecoder.decode(pathToDecode, "UTF-8");
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return decoded;
    }
}
