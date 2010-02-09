function encrypt(i_user, i_pass) {
    var username = i_user.value;
    var password = i_pass.value;

    if (username.length < 3 || password.length < 3) {
        alert ("Invalid Username and/or Password.");
        return false;
    }
    var seed     = readCookie ("JSESSIONID");
    var pass     = hex_md5 (username + password);

    i_pass.readOnly = true;
    i_pass.value = hex_md5 (seed + pass);
}