// ==================== METRICS PAGE SCRIPT ====================

// Simulated user data (depois vem do backend)
const userData = {
    name: "Felipe Napoli",
    email: "felipe@example.com",
    age: 25,
    weight: 80,
    height: 180,
    profileImg: "https://via.placeholder.com/120",
    dailyTarget: {
        calories: 2500,
        protein: 150,
        carbs: 400,
        fat: 90
    }
};

const todayData = {
    calories: 1850,
    protein: 95,
    carbs: 235,
    fat: 65,
    water: 4,
    steps: 7340,
    caloriesBurned: 380,
    exerciseTime: 45
};

// Initialize dashboard
document.addEventListener('DOMContentLoaded', () => {
    populateUserData();
    updateMetrics();
    setupEventListeners();
    animateProgressBars();
});

// Populate user information
function populateUserData() {
    document.getElementById('userName').textContent = userData.name;
    document.getElementById('userEmail').textContent = userData.email;
    document.getElementById('userAge').textContent = 
        `${userData.age} anos • ${userData.weight}kg • ${userData.height/100}m`;
    document.getElementById('profileImg').src = userData.profileImg;
}

// Update metrics display
function updateMetrics() {
    // Calorias
    document.getElementById('consumedCals').textContent = todayData.calories.toLocaleString();
    document.getElementById('targetCals').textContent = userData.dailyTarget.calories.toLocaleString();
    
    const remaining = userData.dailyTarget.calories - todayData.calories;
    document.getElementById('remainingCals').textContent = 
        remaining > 0 ? `${remaining} kcal restantes` : `Excedeu ${Math.abs(remaining)} kcal`;

    // Calorie progress circle
    updateProgressCircle();

    // Macronutrients
    document.getElementById('proteinVal').textContent = todayData.protein;
    document.getElementById('proteinTarget').textContent = userData.dailyTarget.protein;
    document.getElementById('carbsVal').textContent = todayData.carbs;
    document.getElementById('carbsTarget').textContent = userData.dailyTarget.carbs;
    document.getElementById('fatVal').textContent = todayData.fat;
    document.getElementById('fatTarget').textContent = userData.dailyTarget.fat;

    // Update macro bars width
    const proteinPercent = (todayData.protein / userData.dailyTarget.protein) * 100;
    const carbsPercent = (todayData.carbs / userData.dailyTarget.carbs) * 100;
    const fatPercent = (todayData.fat / userData.dailyTarget.fat) * 100;

    document.getElementById('proteinBar').style.width = Math.min(proteinPercent, 100) + '%';
    document.getElementById('carbsBar').style.width = Math.min(carbsPercent, 100) + '%';
    document.getElementById('fatBar').style.width = Math.min(fatPercent, 100) + '%';

    // Water intake
    document.getElementById('waterCups').textContent = todayData.water;

    // Activity
    document.getElementById('stepsVal').textContent = todayData.steps.toLocaleString();
    document.getElementById('caloriesBurned').textContent = todayData.caloriesBurned;
    document.getElementById('exerciseMin').textContent = todayData.exerciseTime;
}

// Update calorie progress circle
function updateProgressCircle() {
    const percent = (todayData.calories / userData.dailyTarget.calories) * 100;
    const circumference = 2 * Math.PI * 45; // raio = 45
    const strokeDashoffset = circumference - (percent / 100) * circumference;
    
    const progressFill = document.getElementById('calorieProgress');
    progressFill.style.strokeDashoffset = strokeDashoffset;

    // Change color based on progress
    if (percent <= 70) {
        progressFill.style.stroke = 'var(--primary)';
    } else if (percent <= 100) {
        progressFill.style.stroke = '#FFA500';
    } else {
        progressFill.style.stroke = '#FF6B6B';
    }
}

// Animate progress bars on load
function animateProgressBars() {
    const bars = document.querySelectorAll('.macro-fill');
    bars.forEach((bar, index) => {
        setTimeout(() => {
            bar.style.animation = 'none';
            setTimeout(() => {
                const width = bar.style.width;
                bar.style.width = '0';
                setTimeout(() => {
                    bar.style.transition = 'width 1s ease';
                    bar.style.width = width;
                }, 10);
            }, 10);
        }, index * 100);
    });
}

// Event listeners
function setupEventListeners() {
    // Logout button
    document.getElementById('logoutBtn').addEventListener('click', () => {
        if (confirm('Tem certeza que deseja fazer logout?')) {
            localStorage.removeItem('authToken');
            window.location.href = '../index.html';
        }
    });

    // Water cups interaction
    const waterCups = document.querySelectorAll('.water-cup');
    waterCups.forEach((cup, index) => {
        cup.addEventListener('click', () => {
            waterCups.forEach((c, i) => {
                if (i <= index) {
                    c.classList.add('filled');
                } else {
                    c.classList.remove('filled');
                }
            });
            todayData.water = index + 1;
            document.getElementById('waterCups').textContent = todayData.water;
        });
    });

    // Add Meal button
    document.getElementById('addMealBtn').addEventListener('click', () => {
        alert('Funcionalidade de adicionar refeição será implementada em breve!');
    });

    // View Reports button
    document.getElementById('viewReportsBtn').addEventListener('click', () => {
        alert('Relatórios serão implementados em breve!');
    });

    // Edit Goals button
    document.getElementById('editGoalsBtn').addEventListener('click', () => {
        alert('Edição de objetivos será implementada em breve!');
    });

    // Hamburger menu
    const hamburger = document.getElementById('hamburger');
    const navLinks = document.querySelector('.nav-links');
    
    hamburger.addEventListener('click', () => {
        hamburger.classList.toggle('active');
        if (navLinks.style.display === 'flex') {
            navLinks.style.display = 'none';
        } else {
            navLinks.style.display = 'flex';
            navLinks.style.position = 'absolute';
            navLinks.style.top = '60px';
            navLinks.style.left = '0';
            navLinks.style.right = '0';
            navLinks.style.flexDirection = 'column';
            navLinks.style.backgroundColor = 'white';
            navLinks.style.borderBottom = '1px solid var(--border)';
            navLinks.style.padding = '1rem 2rem';
            navLinks.style.gap = '1rem';
            navLinks.style.zIndex = '999';
        }
    });

    // Close menu when clicking on links
    document.querySelectorAll('.nav-links a').forEach(link => {
        link.addEventListener('click', () => {
            hamburger.classList.remove('active');
            navLinks.style.display = 'none';
        });
    });
}

// Function to fetch data from backend (para depois)
async function loadUserMetrics() {
    try {
        const token = localStorage.getItem('authToken');
        if (!token) {
            window.location.href = '../index.html';
            return;
        }

        // const response = await fetch('http://localhost:8080/api/metrics', {
        //     headers: {
        //         'Authorization': `Bearer ${token}`
        //     }
        // });
        // const data = await response.json();
        // Object.assign(todayData, data);
        // updateMetrics();
    } catch (error) {
        console.error('Erro ao carregar métricas:', error);
    }
}
