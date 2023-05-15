const url = "http://localhost:3000/users"

async function render() {
    if (localStorage.currentUser) {
        const query = url + "/" + localStorage.getItem("currentUser");
    try {
        const response = await fetch(query);
        const jsonData = await response.json();
        console.log(jsonData);
        if (jsonData) {
            document.getElementById("mainbody").innerHTML = "<h2>Your Info</h2><p>Your Name: " + jsonData.name
            + "</p><p>Your Email: " + jsonData.email + "</p><p>Your Username: " + jsonData.username +
            "</p><p>Your Address: " + jsonData.address + "</p>";
        }
        else {
            console.log("Error getting data");
        }
    } catch (error) {
        console.log(error);
    }
    } else {
        document.getElementById("mainbody").innerHTML = "<p>You are not logged in!</p>";
    }
}