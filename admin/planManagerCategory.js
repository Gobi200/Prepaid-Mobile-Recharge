document.addEventListener("DOMContentLoaded", function () {
    fetchCategories();

    // Event listener for saving edited category
    document.getElementById("saveEdit").addEventListener("click", function () {
        updateCategory();
    });

    // Event listener for adding a new category
    document.getElementById("saveCategory").addEventListener("click", function () {
        addCategory();
    });
});

function fetchCategories() {
    fetch("http://localhost:8091/category")
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(categories => {
            const tableBody = document.getElementById("categoryTableBody");
            tableBody.innerHTML = ""; // Clear existing rows

            categories.forEach(category => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${category.categoryId}</td>
                    <td>${category.categoryName}</td>
                    <td class="fw-bold ${category.status === 'ACTIVE' ? 'text-success' : 'text-danger'}"> ${category.status} </td>
                    <td class="d-flex gap-2">
                        <button class="btn btn-warning btn-sm" onclick="editCategory('${category.categoryId}', '${category.categoryName.replace(/'/g, "\\'")}')"><i class="bi bi-pencil-fill pe-1"></i> Edit</button>
                        <button class="btn ${category.status === 'ACTIVE' ? 'btn-danger' : 'btn-success'} btn-sm" 
                            onclick="toggleCategoryStatus('${category.categoryId}', '${category.status === 'ACTIVE' ? 'INACTIVE' : 'ACTIVE'}')">
                            ${category.status === 'ACTIVE' ? 'Deactivate' : 'Activate'}
                        </button>
                    </td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => console.error("Error fetching categories:", error));
}

function toggleCategoryStatus(categoryId, newStatus) {
    const confirmMessage = newStatus === "INACTIVE" 
        ? "Are you sure you want to deactivate this category?" 
        : "Are you sure you want to activate this category?";

    if (confirm(confirmMessage)) {
        fetch(`http://localhost:8091/category/${categoryId}/${newStatus.toLowerCase()}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to update category status");
            }
            return response.text();
        })
        .then(() => {
            showToast(`Category ${newStatus.toLowerCase()}d successfully`);
            fetchCategories(); // Refresh the table
        })
        .catch(error => console.error("Error updating category status:", error));
    }
}

// Function to show Bootstrap toast
function showToast(message) {
    const toastMessage = document.getElementById("toastMessage");
    toastMessage.textContent = message;

    const toastElement = new bootstrap.Toast(document.getElementById("statusToast"));
    toastElement.show();
}


function addCategory() {
    const categoryName = document.getElementById("categoryName").value.trim();

    if (!categoryName) {
        alert("Category name cannot be empty!");
        return;
    }

    fetch("http://localhost:8091/category", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ categoryName: categoryName })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Failed to add category");
        }
        return response.json();
    })
    .then(data => {
        console.log("Category added successfully:", data);
        fetchCategories(); // Refresh the table

        // Close modal after adding
        let addModal = bootstrap.Modal.getInstance(document.getElementById("addCategoryModal"));
        addModal.hide();

        // Reset input field
        document.getElementById("categoryName").value = "";
    })
    .catch(error => {
        console.error("Error adding category:", error);
        alert("Error adding category. Please try again.");
    });
}

function editCategory(categoryId, categoryName) {
    console.log("Opening edit modal for:", categoryId, categoryName); // Debugging
    document.getElementById("editCategoryName").value = categoryName;
    document.getElementById("saveEdit").setAttribute("data-id", categoryId);

    let editModal = new bootstrap.Modal(document.getElementById("editCategoryModal"));
    editModal.show();
}

function updateCategory() {
        const categoryId = document.getElementById("saveEdit").getAttribute("data-id");
        const updatedCategoryName = document.getElementById("editCategoryName").value;

        if (!updatedCategoryName.trim()) {
            alert("Category name cannot be empty!");
            return;
        }

        fetch(`http://localhost:8091/category/${categoryId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ categoryName: updatedCategoryName })
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to update category");
            }
            return response.json();
        })
        .then(data => {
            console.log("Category updated successfully:", data);
            fetchCategories(); // Refresh the table

            // Show Bootstrap Toast
            let toast = new bootstrap.Toast(document.getElementById("updateToast"));
            toast.show();

            // Close modal after update
            let editModal = bootstrap.Modal.getInstance(document.getElementById("editCategoryModal"));
            editModal.hide();
        })
        .catch(error => {
            console.error("Error updating category:", error);
            alert("Error updating category. Please try again.");
        });
    }