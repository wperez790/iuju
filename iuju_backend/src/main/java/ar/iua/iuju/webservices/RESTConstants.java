package ar.iua.iuju.webservices;

public class RESTConstants {
    static final int HTTP_RESPONSE_OK = 200;
    static final int HTTP_RESPONSE_CREATED = 201;
    static final int HTTP_RESPONSE_BAD_REQUEST = 400;
    static final int HTTP_RESPONSE_UNAUTHORIZED = 401;
    static final int HTTP_RESPONSE_FORBIDDEN = 403;
    static final int HTTP_RESPONSE_NOT_FOUND = 404;
    static final int HTTP_RESPONSE_INTERNAL_SERVER_ERROR = 500;

    public static final String HTTPS_PROTOCOL = "HTTPS";
    public static final String CONTENT_TYPE_JSON = "application/json";
    static final String CONTENT_TYPE_FORM_DATA = "multipart/form-data";
    
    public static final String URL_BASE = "/api";

    public static final String URL_LOGIN = URL_BASE + "/login";
    public static final String URL_USERS = URL_BASE + "/usuarios";
    public static final String URL_TAREAS = URL_BASE + "/tareas";
    public static final String URL_EVENTOS = URL_BASE + "/eventos";
    public static final String URL_NOTAS = URL_BASE + "/notas";
}