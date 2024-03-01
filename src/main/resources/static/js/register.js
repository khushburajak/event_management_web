function validateRegister() {
    let n = document.getElementById("name").value
    let em = document.getElementById("email").value
    let num = document.getElementById("number").value
    let add = document.getElementById("address").value
    let cp = document.getElementById("change-password").value
    let cn = document.getElementById("confirm-password").value
    let btn = document.getElementById("register-btn")
    if (n === "") {
        alert("Enter your name")
    }else if (em === "") {
        alert("Enter your email address")
    }else if (num === "") {
        alert("Enter your name")
    }else if (add === "") {
        alert("Enter your address")
    }else if (cp === "") {
        alert("Create your password")
    }else if (!cp.match(cn)) {
        alert("Make sure to match password before we create your account")
        return false
    } else if (cp.match(cn)) {
        return true
    }
}