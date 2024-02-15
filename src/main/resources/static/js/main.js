function check(){
    if(confirm("로그아웃 하시겠습니까?")) {
        alert("로그아웃 되었습니다!")
        location.href = '/user/logout';
        return true;
    }
    else {
        return false;
    }
}