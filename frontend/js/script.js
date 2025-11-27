// ==================== MODAL CONTROLS ====================
const loginBtn = document.getElementById('loginBtn');
const loginModal = document.getElementById('loginModal');
const signupModal = document.getElementById('signupModal');
const closeLogin = document.getElementById('closeLogin');
const closeSignup = document.getElementById('closeSignup');
const switchToSignup = document.getElementById('switchToSignup');
const switchToLogin = document.getElementById('switchToLogin');

// Modal Event Listeners
loginBtn.addEventListener('click', () => {
    loginModal.classList.add('active');
});

document.querySelector('.btn-signup').addEventListener('click', () => {
    signupModal.classList.add('active');
});

closeLogin.addEventListener('click', () => {
    loginModal.classList.remove('active');
});

closeSignup.addEventListener('click', () => {
    signupModal.classList.remove('active');
});

switchToSignup.addEventListener('click', (e) => {
    e.preventDefault();
    loginModal.classList.remove('active');
    signupModal.classList.add('active');
});

switchToLogin.addEventListener('click', (e) => {
    e.preventDefault();
    signupModal.classList.remove('active');
    loginModal.classList.add('active');
});

// Close modal when clicking outside
window.addEventListener('click', (e) => {
    if (e.target === loginModal) {
        loginModal.classList.remove('active');
    }
    if (e.target === signupModal) {
        signupModal.classList.remove('active');
    }
});

// ==================== FORM HANDLING ====================
document.getElementById('loginForm').addEventListener('submit', (e) => {
    e.preventDefault();
    console.log('Login attempt');
    // Aqui você conectará com o backend
});

document.getElementById('signupForm').addEventListener('submit', (e) => {
    e.preventDefault();
    console.log('Signup attempt');
    // Aqui você conectará com o backend
});

// ==================== COUNTER ANIMATION ====================
function animateCounters() {
    const counters = document.querySelectorAll('.metric-number');
    
    counters.forEach(counter => {
        const target = parseInt(counter.getAttribute('data-target'));
        const increment = target / 100;
        let current = 0;

        const updateCounter = () => {
            if (current < target) {
                current += increment;
                counter.textContent = Math.ceil(current).toLocaleString() + (counter.textContent.includes('%') ? '%' : '+');
                setTimeout(updateCounter, 20);
            } else {
                counter.textContent = target.toLocaleString() + (counter.textContent.includes('%') ? '%' : '+');
            }
        };

        // Inicia animação quando elemento entra na viewport
        const observer = new IntersectionObserver(entries => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    updateCounter();
                    observer.unobserve(counter);
                }
            });
        });

        observer.observe(counter);
    });
}

animateCounters();

// ==================== HAMBURGER MENU ====================
const hamburger = document.getElementById('hamburger');
let navLinksContainer = document.querySelector('.nav-links');

hamburger.addEventListener('click', () => {
    hamburger.classList.toggle('active');
    if (navLinksContainer.style.display === 'flex') {
        navLinksContainer.style.display = 'none';
    } else {
        navLinksContainer.style.display = 'flex';
        navLinksContainer.style.position = 'absolute';
        navLinksContainer.style.top = '60px';
        navLinksContainer.style.left = '0';
        navLinksContainer.style.right = '0';
        navLinksContainer.style.flexDirection = 'column';
        navLinksContainer.style.backgroundColor = 'white';
        navLinksContainer.style.borderBottom = '1px solid var(--border)';
        navLinksContainer.style.padding = '1rem 2rem';
        navLinksContainer.style.gap = '1rem';
    }
});

// Close hamburger when clicking on a link
document.querySelectorAll('.nav-links a').forEach(link => {
    link.addEventListener('click', () => {
        hamburger.classList.remove('active');
        navLinksContainer.style.display = 'none';
    });
});

// ==================== SMOOTH SCROLL ====================
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        const target = document.querySelector(this.getAttribute('href'));
        if (target) {
            target.scrollIntoView({ behavior: 'smooth' });
        }
    });
});

// ==================== BUTTON RIPPLE EFFECT ====================
function createRipple(event) {
    const button = event.currentTarget;
    const rect = button.getBoundingClientRect();
    const size = Math.max(rect.width, rect.height);
    const x = event.clientX - rect.left - size / 2;
    const y = event.clientY - rect.top - size / 2;

    const ripple = document.createElement('span');
    ripple.style.width = ripple.style.height = size + 'px';
    ripple.style.left = x + 'px';
    ripple.style.top = y + 'px';
    ripple.classList.add('ripple');

    button.appendChild(ripple);

    setTimeout(() => ripple.remove(), 600);
}

document.querySelectorAll('.btn-cta, .btn-cta-large, .btn-form, .btn-signup').forEach(button => {
    button.addEventListener('click', createRipple);
});

// ==================== SCROLL ANIMATIONS ====================
const observerOptions = {
    threshold: 0.1,
    rootMargin: '0px 0px -50px 0px'
};

const observer = new IntersectionObserver(entries => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.style.opacity = '1';
            entry.target.style.transform = 'translateY(0)';
        }
    });
}, observerOptions);

document.querySelectorAll('.feature-card, .footer-section').forEach(el => {
    el.style.opacity = '0';
    el.style.transform = 'translateY(20px)';
    el.style.transition = 'opacity 0.6s ease, transform 0.6s ease';
    observer.observe(el);
});

// ==================== ACTIVE NAV LINK ====================
window.addEventListener('scroll', () => {
    const sections = document.querySelectorAll('section');
    const navLinks = document.querySelectorAll('.nav-links a');

    let current = '';
    sections.forEach(section => {
        const sectionTop = section.offsetTop;
        if (pageYOffset >= sectionTop - 200) {
            current = section.getAttribute('id');
        }
    });

    navLinks.forEach(link => {
        link.classList.remove('active');
        if (link.getAttribute('href').slice(1) === current) {
            link.style.color = 'var(--primary)';
        } else {
            link.style.color = 'var(--text)';
        }
    });
});

// ==================== STICKY HEADER ====================
const header = document.querySelector('.header');
let lastScroll = 0;

window.addEventListener('scroll', () => {
    const scroll = window.pageYOffset;

    if (scroll > lastScroll && scroll > 100) {
        // Scrolling down
        header.style.boxShadow = 'none';
        header.style.borderBottom = '1px solid var(--border)';
    } else {
        // Scrolling up
        header.style.boxShadow = '0 4px 12px rgba(0, 0, 0, 0.08)';
    }

    lastScroll = scroll;
});

// ==================== PARALLAX EFFECT ====================
window.addEventListener('scroll', () => {
    const scrollPos = window.pageYOffset;
    const hero = document.querySelector('.hero');
    
    if (hero) {
        hero.style.backgroundPosition = `0% ${scrollPos * 0.5}px`;
    }
});

// ==================== PAGE LOAD ANIMATION ====================
window.addEventListener('load', () => {
    document.body.style.opacity = '1';
});

// Inicia a página com opacidade 0
document.body.style.opacity = '0.95';
