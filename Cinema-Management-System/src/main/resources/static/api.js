/**
 * This file contains all the functions for making API calls to the backend.
 * It abstracts the fetch logic, making the main script.js cleaner.
 */

const API_BASE_URL = 'http://localhost:8080';

/**
 * A helper function to handle fetch responses.
 * @param {Response} response - The response object from a fetch call.
 * @returns {Promise<any>} - The JSON data from the response.
 * @throws {Error} - Throws an error if the response is not ok.
 */
async function handleResponse(response) {
    if (response.ok) {
        // If the response is successful but has no content
        if (response.status === 204 || response.headers.get("Content-Length") === "0") {
            return Promise.resolve(null); 
        }
        return response.json();
    } else {
        const errorText = await response.text();
        console.error('API Error:', errorText);
        throw new Error(errorText || `HTTP error! status: ${response.status}`);
    }
}

/**
 * A helper function to construct URL-encoded form data for POST requests.
 * @param {object} data - The data object to be sent.
 * @returns {URLSearchParams} - The URL-encoded data.
 */
function createBody(data) {
    const body = new URLSearchParams();
    for (const key in data) {
        if (data[key] !== undefined && data[key] !== null) {
            body.append(key, data[key]);
        }
    }
    return body;
}

// --- Authenticator API ---
const AuthAPI = {
    register: (username, password) => {
        return fetch(`${API_BASE_URL}/Authenticator/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: createBody({ username, password })
        }).then(handleResponse);
    },
    loginUser: (username, password) => {
        return fetch(`${API_BASE_URL}/Authenticator/login/user`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: createBody({ username, password })
        }).then(handleResponse);
    },
    loginAdmin: (username, password) => {
        return fetch(`${API_BASE_URL}/Authenticator/login/admin`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: createBody({ username, password })
        }).then(handleResponse);
    }
};

// --- Film API ---
const FilmAPI = {
    // For Users
    getAllForUser: (username, password) => {
        return fetch(`${API_BASE_URL}/films/showAlluser`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: createBody({ username, password })
        }).then(handleResponse);
    },
    // For Admins
    getAllForAdmin: (username, password) => {
        return fetch(`${API_BASE_URL}/films/showAlladmin`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: createBody({ username, password })
        }).then(handleResponse);
    },
    create: (credentials, filmData) => {
        const body = { ...credentials, ...filmData };
        return fetch(`${API_BASE_URL}/films/create`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: createBody(body)
        }).then(handleResponse);
    },
    update: (credentials, filmUpdateData) => {
        const body = { ...credentials, ...filmUpdateData };
        return fetch(`${API_BASE_URL}/films/update`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: createBody(body)
        }).then(handleResponse);
    },
    delete: (username, password, filmId) => {
        return fetch(`${API_BASE_URL}/films/delete`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: createBody({ username, password, filmId })
        }).then(handleResponse);
    }
};

// --- Schedule API ---
const ScheduleAPI = {
    // The endpoint in JadwalController is /showAll for both users and admins
    getAll: (username, password) => {
        return fetch(`${API_BASE_URL}/scheduls/showAll`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: createBody({ username, password })
        }).then(handleResponse);
    },
    create: (credentials, scheduleData) => {
        const body = { ...credentials, ...scheduleData };
        return fetch(`${API_BASE_URL}/scheduls/create`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: createBody(body)
        }).then(handleResponse);
    },
    update: (credentials, scheduleUpdateData) => {
         const body = { ...credentials, ...scheduleUpdateData };
        return fetch(`${API_BASE_URL}/scheduls/update`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: createBody(body)
        }).then(handleResponse);
    },
    delete: (username, password, jadwalId) => {
        return fetch(`${API_BASE_URL}/scheduls/delete`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: createBody({ username, password, jadwalId })
        }).then(handleResponse);
    }
};

// --- Payment API ---
const PaymentAPI = {
    buyTicket: (credentials, ticketData) => {
        const body = { ...credentials, ...ticketData };
        return fetch(`${API_BASE_URL}/payments/buy`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: createBody(body)
        }).then(handleResponse);
    },
    // Note: The original controller has @RequestBody User user which is unusual for form data.
    // Assuming we can pass userId instead. If not, this needs adjustment.
    getHistory: (username, password, userId) => {
        const body = createBody({ username, password });
        // The endpoint is /userTicket. We need to clarify how the user object is passed.
        // A common RESTful approach would be /userTicket/{userId} with GET.
        // Sticking to the POST from the controller for now.
        // Sending user object in POST body with application/json would be better.
        // Let's assume the controller can be changed to accept userId.
        return fetch(`${API_BASE_URL}/payments/userTicket`, {
             method: 'POST',
             headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
             body: createBody({ username, password, userId: userId }) // Hypothetical change
        }).then(handleResponse);
    }
};