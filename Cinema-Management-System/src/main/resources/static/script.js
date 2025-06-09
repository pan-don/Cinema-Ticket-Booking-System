/**
 * This file handles all the frontend logic, user interactions,
 * and DOM manipulation. It uses the functions from api.js to
 * communicate with the backend.
 */
document.addEventListener('DOMContentLoaded', () => {

    // --- State Management ---
    // This object will hold the current state of the application
    const AppState = {
        currentUser: null, // { username, password, role, id }
        movies: [],
        schedules: [],
    };

    // --- DOM Element Cache ---
    const DOMElements = {
        pages: document.querySelectorAll('.page'),
        appHeader: document.getElementById('app-header'),
        loginForm: document.getElementById('login-form'),
        registerForm: document.getElementById('register-form'),
        roleDropdown: document.querySelector('.custom-dropdown'),
        roleDropdownBtn: document.getElementById('role-dropdown-btn'),
        selectedRoleSpan: document.getElementById('selected-role'),
        dropdownMenu: document.querySelector('.dropdown-menu'),
        movieListContainer: document.getElementById('movie-list'),
        viewHistoryBtn: document.getElementById('view-history-btn'),
        manageMoviesBtn: document.getElementById('manage-movies-btn'),
        manageSchedulesBtn: document.getElementById('manage-schedules-btn'),
        backToAdminBtns: document.querySelectorAll('.back-to-admin-btn'),
        movieForm: document.getElementById('movie-form'),
        movieFormTitle: document.getElementById('movie-form-title'),
        clearMovieFormBtn: document.getElementById('clear-movie-form-btn'),
        existingMoviesList: document.getElementById('existing-movies-list'),
        scheduleForm: document.getElementById('schedule-form'),
        scheduleFormTitle: document.getElementById('schedule-form-title'),
        clearScheduleFormBtn: document.getElementById('clear-schedule-form-btn'),
        existingSchedulesList: document.getElementById('existing-schedules-list'),
        scheduleFilmelect: document.getElementById('schedule-filmId'),
        movieDetailsModal: document.getElementById('movie-details-modal'),
        movieDetailsContent: document.getElementById('movie-details-content'),
        purchaseHistoryModal: document.getElementById('purchase-history-modal'),
        purchaseHistoryContent: document.getElementById('purchase-history-content'),
        alertModal: document.getElementById('alert-modal'),
        alertMessage: document.getElementById('alert-message'),
        alertOkBtn: document.getElementById('alert-ok-btn'),
    };

    // --- UI Logic ---

    /**
     * Shows a specific page by its ID and hides others.
     * @param {string} pageId The ID of the page element to show.
     */
    function showPage(pageId) {
        DOMElements.pages.forEach(page => page.classList.remove('active'));
        const pageToShow = document.getElementById(pageId);
        if (pageToShow) pageToShow.classList.add('active');
        
        const isAuthPage = pageId === 'login-page' || pageId === 'register-page';
        DOMElements.appHeader.classList.toggle('hidden', isAuthPage);
    }

    /**
     * Displays a custom alert modal with a message.
     * @param {string} message The message to display.
     * @param {boolean} isError If true, displays the message with an error indication.
     */
    function showAlert(message, isError = false) {
        DOMElements.alertMessage.textContent = message;
        DOMElements.alertMessage.style.color = isError ? '#FF5C5C' : 'white';
        DOMElements.alertModal.classList.add('active');
    }

    /**
     * Toggles the visibility of a modal.
     * @param {HTMLElement} modal - The modal element to toggle.
     * @param {boolean} show - True to show, false to hide.
     */
    function toggleModal(modal, show) {
        modal.classList.toggle('active', show);
    }

    // --- Render Functions ---

    /**
     * Renders the list of movies for the user dashboard.
     */
    function renderUserMovies() {
        DOMElements.movieListContainer.innerHTML = '';
        if (AppState.movies.length === 0) {
            DOMElements.movieListContainer.innerHTML = `<p class="text-center w-full text-gray">No movies currently available.</p>`;
            return;
        }

        const moviesWithSchedules = AppState.movies.filter(movie =>
            AppState.schedules.some(s => s.filmId === movie.id)
        );

        moviesWithSchedules.forEach(movie => {
            const card = document.createElement('div');
            card.className = 'flex-shrink-0 w-64 p-4';
            card.innerHTML = `
                <div class="bg-dark-gray rounded-lg shadow-lg overflow-hidden transform hover:scale-105 transition-transform duration-300">
                    <img src="https://placehold.co/400x600/1E1E1E/FFFFFF?text=${movie.judul.replace(/\s/g, '+')}" alt="${movie.judul}" class="w-full h-80 object-cover cursor-pointer" data-movie-id="${movie.id}">
                    <div class="p-4">
                        <h3 class="font-bold text-lg text-white">${movie.judul}</h3>
                        <p class="text-gray text-sm mt-1">${movie.genre}</p>
                        <button class="mt-4 w-full bg-btn-red hover:bg-red-700 text-white font-bold py-2 px-4 rounded-md transition duration-300 view-details-btn" data-movie-id="${movie.id}">
                            View Details & Book
                        </button>
                    </div>
                </div>
            `;
            DOMElements.movieListContainer.appendChild(card);
        });
    }

    /**
     * Renders the movie details modal.
     * @param {string} movieId - The ID of the movie to show details for.
     */
    function renderMovieDetailsModal(movieId) {
        const movie = AppState.movies.find(m => m.id === movieId);
        const schedules = AppState.schedules.filter(s => s.filmId === movieId);
        const availableSeats = movie.kapasitasRuangan - (movie.tiketTerjual || 0);

        DOMElements.movieDetailsContent.innerHTML = `
            <div class="flex justify-between items-center mb-4">
                <h2 class="text-2xl font-bold text-white">${movie.judul}</h2>
                <button class="close-modal-btn text-2xl text-gray hover:text-white">&times;</button>
            </div>
            <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
                <img src="https://placehold.co/400x600/1E1E1E/FFFFFF?text=${movie.judul.replace(/\s/g, '+')}" alt="${movie.judul}" class="w-full h-auto object-cover rounded-md col-span-1">
                <div class="col-span-1 md:col-span-2 text-white">
                    <p class="text-highlight font-semibold">${movie.genre} | ${movie.durasi.hour || 0}h ${movie.durasi.minute || 0}m</p>
                    <p class="text-gray mt-4">${movie.sinopsis}</p>
                    <p class="mt-4"><span class="font-bold">Room:</span> ${movie.ruangan}</p>
                    <p><span class="font-bold">Available Seats:</span> ${availableSeats}</p>
                    <p class="text-2xl font-bold mt-4">Rp ${movie.harga.toLocaleString('id-ID')}</p>
                    <div class="mt-6">
                        <label for="schedule-select" class="text-sm text-gray">Select Schedule:</label>
                        <select id="schedule-select" class="w-full mt-1 p-2 bg-[#333] rounded-md border-gray border text-white">
                            ${schedules.map(s => `<option value="${s.id}">${s.tanggalTayang} at ${s.jamTayang}</option>`).join('')}
                        </select>
                    </div>
                    <div class="mt-4">
                        <label for="quantity-select" class="text-sm text-gray">Quantity:</label>
                        <input type="number" id="quantity-select" value="1" min="1" max="${availableSeats}" class="w-full mt-1 p-2 bg-[#333] rounded-md border-gray border text-white">
                    </div>
                    <button id="confirm-booking-btn" data-movie-id="${movie.id}" class="mt-6 w-full bg-btn-red hover:bg-red-700 text-white font-bold py-3 rounded-md transition duration-300">
                        Book Ticket
                    </button>
                </div>
            </div>
        `;
        toggleModal(DOMElements.movieDetailsModal, true);
    }

    /**
     * Renders the list of movies in the admin management table.
     */
    function renderAdminMovies() {
        DOMElements.existingMoviesList.innerHTML = '';
        AppState.movies.forEach(movie => {
            const row = document.createElement('tr');
            row.className = 'border-b border-gray/50';
            row.innerHTML = `
                <td class="p-2">${movie.judul}</td>
                <td class="p-2">${movie.genre}</td>
                <td class="p-2">${movie.ruangan}</td>
                <td class="p-2">Rp ${movie.harga.toLocaleString('id-ID')}</td>
                <td class="p-2 space-x-2">
                    <button class="update-movie-btn text-blue-400 hover:text-blue-300" data-id="${movie.id}"><i class="fas fa-edit"></i></button>
                    <button class="delete-movie-btn text-red-500 hover:text-red-400" data-id="${movie.id}"><i class="fas fa-trash"></i></button>
                </td>
            `;
            DOMElements.existingMoviesList.appendChild(row);
        });
    }

    /**
     * Renders the list of schedules in the admin management table.
     */
    function renderAdminSchedules() {
        DOMElements.existingSchedulesList.innerHTML = '';
        DOMElements.scheduleFilmelect.innerHTML = '<option value="">Select a movie</option>';

        AppState.movies.forEach(movie => {
            DOMElements.scheduleFilmelect.innerHTML += `<option value="${movie.id}">${movie.judul}</option>`;
        });

        AppState.schedules.forEach(schedule => {
            const movie = AppState.movies.find(m => m.id === schedule.filmId);
            const row = document.createElement('tr');
            row.className = 'border-b border-gray/50';
            row.innerHTML = `
                <td class="p-2">${movie ? movie.judul : 'Unknown Movie'}</td>
                <td class="p-2">${schedule.tanggalTayang}</td>
                <td class="p-2">${schedule.jamTayang}</td>
                <td class="p-2 space-x-2">
                    <button class="update-schedule-btn text-blue-400 hover:text-blue-300" data-id="${schedule.id}"><i class="fas fa-edit"></i></button>
                    <button class="delete-schedule-btn text-red-500 hover:text-red-400" data-id="${schedule.id}"><i class="fas fa-trash"></i></button>
                </td>
            `;
            DOMElements.existingSchedulesList.appendChild(row);
        });
    }
    
    // --- Event Handlers ---

    function handleLoginFormSubmit(e) {
        e.preventDefault();
        const username = DOMElements.loginForm.elements['login-username'].value;
        const password = DOMElements.loginForm.elements['login-password'].value;
        const role = DOMElements.selectedRoleSpan.textContent;

        const loginPromise = role === 'USER' 
            ? AuthAPI.loginUser(username, password)
            : AuthAPI.loginAdmin(username, password);

        loginPromise.then(user => {
            if (user) {
                AppState.currentUser = { username, password, role, id: user.id };
                if (role === 'USER') {
                    loadUserDashboard();
                } else {
                    loadAdminDashboard();
                }
            } else {
                throw new Error("Login failed. Please check your credentials.");
            }
        }).catch(err => showAlert(err.message, true));
    }

    function handleRegisterFormSubmit(e) {
        e.preventDefault();
        const username = DOMElements.registerForm.elements['register-username'].value;
        const password = DOMElements.registerForm.elements['register-password'].value;
        AuthAPI.register(username, password).then(() => {
            showAlert("Registration successful! Please log in.");
            DOMElements.registerForm.reset();
            showPage('login-page');
        }).catch(err => showAlert(err.message, true));
    }

    function handleLogout() {
        AppState.currentUser = null;
        AppState.movies = [];
        AppState.schedules = [];
        DOMElements.loginForm.reset();
        showPage('login-page');
    }
    
    async function loadUserDashboard() {
        showPage('user-page');
        try {
            const { username, password } = AppState.currentUser;
            const [movies, schedules] = await Promise.all([
                FilmAPI.getAllForUser(username, password),
                ScheduleAPI.getAll(username, password)
            ]);
            AppState.movies = movies || [];
            AppState.schedules = schedules || [];
            renderUserMovies();
        } catch (err) {
            showAlert(err.message, true);
        }
    }

    async function loadAdminDashboard() {
        showPage('admin-page');
         try {
            const { username, password } = AppState.currentUser;
            // Fetch all data needed for admin panels
            const [movies, schedules] = await Promise.all([
                FilmAPI.getAllForAdmin(username, password),
                ScheduleAPI.getAll(username, password)
            ]);
            AppState.movies = movies || [];
            AppState.schedules = schedules || [];
        } catch (err) {
            showAlert(err.message, true);
        }
    }

    function handleViewDetailsClick(e) {
        const target = e.target.closest('.view-details-btn, img[data-movie-id]');
        if (target) {
            renderMovieDetailsModal(target.dataset.movieId);
        }
    }

    async function handleBookingConfirmation(e) {
        const target = e.target.closest('#confirm-booking-btn');
        if (target) {
            const movieId = target.dataset.movieId;
            const movie = AppState.movies.find(m => m.id === movieId);
            const scheduleId = document.getElementById('schedule-select').value;
            const quantity = document.getElementById('quantity-select').value;
            const totalPrice = movie.harga * quantity;

            try {
                await PaymentAPI.buyTicket(AppState.currentUser, {
                    jadwalId: scheduleId,
                    pembayaran: totalPrice,
                    kuantitas: quantity
                });
                showAlert(`Successfully booked ${quantity} ticket(s) for ${movie.judul}!`);
                toggleModal(DOMElements.movieDetailsModal, false);
                // Optionally, refresh movie data to show updated seat counts
                loadUserDashboard();
            } catch (err) {
                showAlert(err.message, true);
            }
        }
    }

    async function handleViewHistory() {
         try {
            const { username, password, id } = AppState.currentUser;
            // The API needs clarification, assuming it can take userId
            const history = await PaymentAPI.getHistory(username, password, id);
            DOMElements.purchaseHistoryContent.innerHTML = '';
            if (!history || history.length === 0) {
                DOMElements.purchaseHistoryContent.innerHTML = `<p class="text-center text-gray">You have no purchase history.</p>`;
            } else {
                 const table = document.createElement('table');
                    table.className = 'min-w-full text-left';
                    table.innerHTML = `
                         <thead>
                            <tr class="border-b border-gray">
                                <th class="p-2">Movie</th>
                                <th class="p-2">Date</th>
                                <th class="p-2">Qty</th>
                                <th class="p-2">Total Price</th>
                            </tr>
                        </thead>
                        <tbody>
                            ${history.map(ticket => `
                                <tr class="border-b border-gray/50">
                                    <td class="p-2">${ticket.movieTitle}</td>
                                    <td class="p-2">${ticket.date} at ${ticket.time}</td>
                                    <td class="p-2">${ticket.kuantitas}</td>
                                    <td class="p-2">Rp ${ticket.totalPrice.toLocaleString('id-ID')}</td>
                                </tr>
                            `).join('')}
                        </tbody>
                    `;
                DOMElements.purchaseHistoryContent.appendChild(table);
            }
            toggleModal(DOMElements.purchaseHistoryModal, true);
        } catch (err) {
            showAlert(err.message, true);
        }
    }
    
    // --- Admin Event Handlers ---

    function handleMovieFormSubmit(e) {
        e.preventDefault();
        const movieData = {
            id: DOMElements.movieForm.elements['movie-id'].value,
            judul: DOMElements.movieForm.elements['movie-judul'].value,
            genre: DOMElements.movieForm.elements['movie-genre'].value,
            sinopsis: DOMElements.movieForm.elements['movie-sinopsis'].value,
            durasi: DOMElements.movieForm.elements['movie-durasi'].value,
            ruangan: DOMElements.movieForm.elements['movie-ruangan'].value,
            kapasitasRuangan: parseInt(DOMElements.movieForm.elements['movie-kapasitas'].value),
            harga: parseInt(DOMElements.movieForm.elements['movie-harga'].value),
            tiketTerjual: 0,
        };

        const apiCall = movieData.id
            ? FilmAPI.update(AppState.currentUser, { filmId: movieData.id, ...movieData })
            : FilmAPI.create(AppState.currentUser, movieData);
        
        apiCall.then(() => {
            showAlert(`Movie ${movieData.id ? 'updated' : 'created'} successfully.`);
            DOMElements.movieForm.reset();
            loadAdminDashboard().then(renderAdminMovies); // Refresh data
        }).catch(err => showAlert(err.message, true));
    }
    
    function handleAdminMovieListClick(e){
        const target = e.target.closest('button');
        if (!target) return;
        
        const movieId = target.dataset.id;
        
        if (target.classList.contains('update-movie-btn')) {
            const movie = AppState.movies.find(m => m.id === movieId);
            DOMElements.movieFormTitle.textContent = 'Update Movie';
            // Populate form...
            Object.keys(movie).forEach(key => {
                const input = DOMElements.movieForm.elements[`movie-${key}`];
                if(input) input.value = movie[key];
            });
             DOMElements.movieForm.elements['movie-id'].value = movie.id;

            window.scrollTo(0, 0);
        } else if (target.classList.contains('delete-movie-btn')) {
            if (confirm('Are you sure you want to delete this movie?')) {
                FilmAPI.delete(AppState.currentUser.username, AppState.currentUser.password, movieId)
                    .then(() => {
                        showAlert('Movie deleted.');
                        loadAdminDashboard().then(renderAdminMovies);
                    })
                    .catch(err => showAlert(err.message, true));
            }
        }
    }
    
    // --- Event Listeners Setup ---
    function setupEventListeners() {
        DOMElements.loginForm.addEventListener('submit', handleLoginFormSubmit);
        DOMElements.registerForm.addEventListener('submit', handleRegisterFormSubmit);
        document.getElementById('logout-btn').addEventListener('click', handleLogout);

        // Page navigation
        document.getElementById('show-register-link').addEventListener('click', (e) => { e.preventDefault(); showPage('register-page'); });
        document.getElementById('show-login-link').addEventListener('click', (e) => { e.preventDefault(); showPage('login-page'); });

        // Role Dropdown
        DOMElements.roleDropdownBtn.addEventListener('click', () => DOMElements.dropdownMenu.classList.toggle('hidden'));
        DOMElements.dropdownMenu.addEventListener('click', (e) => {
            if (e.target.matches('.dropdown-item')) {
                e.preventDefault();
                DOMElements.selectedRoleSpan.textContent = e.target.dataset.value;
                DOMElements.dropdownMenu.classList.add('hidden');
            }
        });
        window.addEventListener('click', (e) => {
            if (!DOMElements.roleDropdown.contains(e.target)) DOMElements.dropdownMenu.classList.add('hidden');
        });

        // Modals
        DOMElements.alertOkBtn.addEventListener('click', () => toggleModal(DOMElements.alertModal, false));
        document.body.addEventListener('click', (e) => {
            if (e.target.matches('.close-modal-btn')) {
                toggleModal(e.target.closest('.modal'), false);
            }
        });
        
        // User Dashboard
        DOMElements.movieListContainer.addEventListener('click', handleViewDetailsClick);
        DOMElements.movieDetailsModal.addEventListener('click', handleBookingConfirmation);
        DOMElements.viewHistoryBtn.addEventListener('click', handleViewHistory);

        // Admin Dashboard
        DOMElements.manageMoviesBtn.addEventListener('click', () => { showPage('admin-movie-management'); renderAdminMovies(); });
        DOMElements.manageSchedulesBtn.addEventListener('click', () => { showPage('admin-schedule-management'); renderAdminSchedules(); });
        DOMElements.backToAdminBtns.forEach(btn => btn.addEventListener('click', () => showPage('admin-page')));

        // Admin Movie Management
        DOMElements.movieForm.addEventListener('submit', handleMovieFormSubmit);
        DOMElements.clearMovieFormBtn.addEventListener('click', () => DOMElements.movieForm.reset());
        DOMElements.existingMoviesList.addEventListener('click', handleAdminMovieListClick);
    }
    
    // --- App Initialization ---
    showPage('login-page');
    setupEventListeners();
});
