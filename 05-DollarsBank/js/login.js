const url = "http://localhost:3000/users"

if (localStorage.currentUser) {
    document.getElementById("mainbody").innerHTML = "<p>You are logged in!</p>";
} else {
    document.getElementById("mainbody").innerHTML = "<h2>Login</h2>" + 
        "<div id='loginform' class='form'>" +
        "Username <input type='text' id='namefield'><br>" +
        "Password <input type='password' id='password'><br><br>" +
        "<button onclick='login()'>Submit</button></form>";
}

async function login() {
    const query = url + "?username=" + document.getElementById("namefield").value + "&password=" + document.getElementById("password").value;
    try {
        const response = await
        fetch(query);
        const jsonData = await
        response.json();
        console.log(jsonData);
        console.log(jsonData[0].id);
        if (jsonData.length === 1) {
            localStorage.setItem("currentUser", jsonData[0].id);
            document.getElementById("mainbody").innerHTML = "<p>You are logged in!</p>";
        }
        else {
            document.getElementById("error").innerHTML = "<p>Error Logging In. Try again</p>";
        }
    } catch (error) {
        console.log(error);
        document.getElementById("error").innerHTML = "<p>Error Logging In. Try again</p>";
    }
}