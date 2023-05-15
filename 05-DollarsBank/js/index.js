const url = "http://localhost:3000/users"

async function render() {
    if (localStorage.currentUser) {
        const id = localStorage.getItem("currentUser");
        console.log("id: " + id);
        let user = await getUser(id);
        console.log(user);
        document.getElementById("mainbody").innerHTML = "<h2>Welcome Back</h2>" 
        + "<h3>How can we help you, " + user.name + "?</h3>"
        + "<ul><li><a href='transaction.html'>Make a Transaction</a></li>"
        + "<li><a href='accounts.html'>View Transaction History</a></li>"
        + "<li><a href='userinfo.html'>View User Details</a></li></ul>";
        
    } else {
        document.getElementById("mainbody").innerHTML = "<h2>Welcome</h2>" + 
        "<p>By registering on this site, you'll be able to manage your bank accounts from anywhere." +
        "Deposit, withdraw, and transfer funds instantly. Sign up now!</p>" +
        "<a href='login.html'>Login</a> <a href='register.html'>Create Account</a>";   
    }
}

async function getUser(id) {
    const query = url + "/" + id;
    try {
        const response = await fetch(query);
        const jsonData = await response.json();
        console.log(jsonData);
        if (jsonData) {
            return jsonData;
        }
        else {
            console.log("Error getting user");
        }
    } catch (error) {
        console.log(error);
    }
}