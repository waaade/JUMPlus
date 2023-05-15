const url = "http://localhost:3000/"

let userId = "";

async function render() {
    if (localStorage.currentUser) {
        userId = localStorage.getItem("currentUser");
        let content = "<h2>Your Account Balances</h2>";
        const accounts = await getAccounts();
        content += "<table><tr><th>Account#</th><th>Type</th><th>Balance</th></tr>";
        accounts.forEach(a => {
            content += "<tr><td>" + a.id + "</td><td>" + a.type + "</td><td>" + a.balance + "</td></tr>";
        })
        content += "</table><h2>Your Recent Transactions</h2>";
        const transactions = await getTransactions();
        content += "<table><tr><th>Transaction#</th><th>Account#</th><th>Type</th><th>Amount</th></tr>";
        transactions.forEach(t => {
            content += "<tr><td>" + t.id + "</td><td>" + t.accountId + "</td><td>" + t.type + "</td><td>" + t.amount + "</td></tr>"
        })
        content += "</table>";
        document.getElementById("mainbody").innerHTML = content;
    } else {
        document.getElementById("mainbody").innerHTML = "<p>You are not logged in!</p>";
    }
}

async function getTransactions() {
    const query = url + "transactions?userId=" + userId + "&_sort=id&_order=desc&_end=5";
    try {
        const response = await fetch(query);
        const jsonData = await response.json();
        console.log(jsonData);
        if (jsonData) {
            return jsonData;
        }
        else {
            console.log("Error getting data");
        }
    } catch (error) {
        console.log(error);
    }
}

// Gets the current user's accounts
async function getAccounts() {
    const query = url + "accounts?userId=" + userId;
    try {
        const response = await fetch(query);
        const jsonData = await response.json();
        console.log(jsonData);
        if (jsonData) {
            return jsonData;
        }
        else {
            console.log("Error getting accounts");
        }
    } catch (error) {
        console.log(error);
    }
}