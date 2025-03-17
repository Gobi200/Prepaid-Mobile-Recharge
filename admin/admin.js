document.addEventListener("DOMContentLoaded", function () {
    const loginForm = document.querySelector("form");

    loginForm.addEventListener("submit", async function (event) {
        event.preventDefault();

        const email = loginForm.querySelector("input[type='email']").value;
        const password = loginForm.querySelector("input[type='password']").value;

        const loginData = { email, password };

        try {
            const response = await fetch("http://localhost:8091/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(loginData),
            });

            if (!response.ok) {
                throw new Error("Login failed. Check your credentials.");
            }

            const data = await response.json();
            localStorage.setItem("token", data.token);

            alert("Login successful!");
            window.location.href = "admin.html"; // Redirect to admin dashboard
        } catch (error) {
            alert(error.message);
        }
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const logoutLink = document.getElementById("offcanvasLogoutLink");

    if (logoutLink) {
        logoutLink.addEventListener("click", function (event) {
            event.preventDefault();
            localStorage.removeItem("token"); // Remove token from local storage
            window.location.href = "adminLogin.html"; // Redirect to login page
        });
    }
});


// // Prevent going back to the previous page
// window.history.pushState(null, "", window.location.href);
// window.onpopstate = function () {
//     history.go(1);
// };

