/* ===== BridgeHope Community Center — Main JavaScript ===== */

// ===== Dark Mode =====
const darkToggle = document.getElementById('darkModeToggle');
const themeIcon = document.getElementById('themeIcon');
const root = document.documentElement;

function setTheme(theme) {
  root.setAttribute('data-theme', theme);
  localStorage.setItem('theme', theme);
  if (themeIcon) {
    themeIcon.className = theme === 'dark' ? 'fas fa-sun' : 'fas fa-moon';
  }
}

const savedTheme = localStorage.getItem('theme') ||
  (window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light');
setTheme(savedTheme);

if (darkToggle) {
  darkToggle.addEventListener('click', () => {
    const current = root.getAttribute('data-theme');
    setTheme(current === 'dark' ? 'light' : 'dark');
  });
}

// ===== Navbar Scroll =====
const navbar = document.querySelector('.navbar-main');
if (navbar) {
  window.addEventListener('scroll', () => {
    navbar.classList.toggle('scrolled', window.scrollY > 50);
  }, { passive: true });
}

// ===== Scroll Animation (Intersection Observer) =====
const animatedElements = document.querySelectorAll('.fade-up, .fade-left, .fade-right');
if (animatedElements.length) {
  const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry, i) => {
      if (entry.isIntersecting) {
        setTimeout(() => entry.target.classList.add('visible'), i * 80);
        observer.unobserve(entry.target);
      }
    });
  }, { threshold: 0.12, rootMargin: '0px 0px -40px 0px' });

  animatedElements.forEach(el => observer.observe(el));
}

// ===== Counter Animation =====
function animateCounter(el) {
  const target = parseInt(el.dataset.target, 10);
  if (isNaN(target)) return;
  const duration = 2000;
  const step = Math.ceil(duration / target);
  let current = 0;
  const timer = setInterval(() => {
    current += Math.max(1, Math.floor(target / 60));
    if (current >= target) {
      current = target;
      clearInterval(timer);
    }
    el.textContent = current.toLocaleString();
  }, step);
}

const counters = document.querySelectorAll('.counter');
if (counters.length) {
  const counterObserver = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        animateCounter(entry.target);
        counterObserver.unobserve(entry.target);
      }
    });
  }, { threshold: 0.5 });
  counters.forEach(c => counterObserver.observe(c));
}

// ===== Donation Amount Buttons =====
const amountBtns = document.querySelectorAll('.donation-amount-btn');
const amountInput = document.getElementById('donationAmount');

if (amountBtns.length && amountInput) {
  amountBtns.forEach(btn => {
    btn.addEventListener('click', function() {
      amountBtns.forEach(b => b.classList.remove('active'));
      if (this.dataset.amount === 'other') {
        amountInput.value = '';
        amountInput.focus();
      } else {
        this.classList.add('active');
        amountInput.value = this.dataset.amount;
      }
    });
  });

  amountInput.addEventListener('input', function() {
    amountBtns.forEach(b => b.classList.remove('active'));
    const otherBtn = document.querySelector('[data-amount="other"]');
    if (otherBtn) otherBtn.classList.add('active');
  });
}

// ===== Donation Type Toggle =====
const donationTypeBtns = document.querySelectorAll('.donation-type-btn');
const donationTypeInput = document.getElementById('donationTypeValue');

if (donationTypeBtns.length) {
  donationTypeBtns.forEach(btn => {
    btn.addEventListener('click', function() {
      donationTypeBtns.forEach(b => b.classList.remove('active'));
      this.classList.add('active');
      if (donationTypeInput) donationTypeInput.value = this.dataset.type;
    });
  });
}

// ===== Smooth Scroll for Anchor Links =====
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
  anchor.addEventListener('click', function(e) {
    const target = document.querySelector(this.getAttribute('href'));
    if (target) {
      e.preventDefault();
      const offset = 80;
      const top = target.getBoundingClientRect().top + window.scrollY - offset;
      window.scrollTo({ top, behavior: 'smooth' });
    }
  });
});

// ===== Mobile Sidebar Toggle (Admin) =====
const sidebarToggle = document.getElementById('sidebarToggle');
const sidebar = document.querySelector('.admin-sidebar');
const overlay = document.getElementById('sidebarOverlay');

if (sidebarToggle && sidebar) {
  sidebarToggle.addEventListener('click', () => {
    sidebar.classList.toggle('open');
    if (overlay) overlay.classList.toggle('d-block');
  });
}
if (overlay) {
  overlay.addEventListener('click', () => {
    sidebar.classList.remove('open');
    overlay.classList.remove('d-block');
  });
}

// ===== Auto-dismiss Alerts =====
document.querySelectorAll('.alert-auto-dismiss').forEach(alert => {
  setTimeout(() => {
    alert.style.transition = 'opacity 0.5s ease';
    alert.style.opacity = '0';
    setTimeout(() => alert.remove(), 500);
  }, 4000);
});

// ===== Form Validation Feedback =====
document.querySelectorAll('form[data-validate]').forEach(form => {
  form.addEventListener('submit', function(e) {
    const required = this.querySelectorAll('[required]');
    let valid = true;
    required.forEach(field => {
      if (!field.value.trim()) {
        field.style.borderColor = 'var(--danger)';
        valid = false;
      } else {
        field.style.borderColor = '';
      }
    });
    if (!valid) {
      e.preventDefault();
      const msg = this.querySelector('.form-error-msg');
      if (msg) msg.style.display = 'block';
    }
  });
});

// ===== Program filter =====
const programSearch = document.getElementById('programSearch');
if (programSearch) {
  programSearch.addEventListener('input', function() {
    const q = this.value.toLowerCase();
    document.querySelectorAll('.program-card-wrap').forEach(card => {
      const text = card.textContent.toLowerCase();
      card.style.display = text.includes(q) ? '' : 'none';
    });
  });
}

// ===== Admin table search =====
const tableSearch = document.getElementById('tableSearch');
if (tableSearch) {
  tableSearch.addEventListener('input', function() {
    const q = this.value.toLowerCase();
    document.querySelectorAll('.admin-table tbody tr').forEach(row => {
      row.style.display = row.textContent.toLowerCase().includes(q) ? '' : 'none';
    });
  });
}

// ===== Confirm delete dialogs =====
document.querySelectorAll('[data-confirm]').forEach(btn => {
  btn.addEventListener('click', function(e) {
    if (!confirm(this.dataset.confirm || 'Are you sure?')) {
      e.preventDefault();
    }
  });
});

// ===== Print receipt =====
const printBtn = document.getElementById('printReceipt');
if (printBtn) printBtn.addEventListener('click', () => window.print());

// ===== Testimonial slider (simple auto-advance) =====
const testimonialCards = document.querySelectorAll('.testimonial-card');
let testimonialIdx = 0;
if (testimonialCards.length > 3) {
  setInterval(() => {
    testimonialCards[testimonialIdx].classList.remove('active');
    testimonialIdx = (testimonialIdx + 1) % testimonialCards.length;
    testimonialCards[testimonialIdx].classList.add('active');
  }, 5000);
}

// ===== Active nav link =====
const currentPath = window.location.pathname;
document.querySelectorAll('.nav-link-main').forEach(link => {
  const href = link.getAttribute('href');
  if (href && currentPath === href) link.classList.add('active');
  else if (href && href !== '/' && currentPath.startsWith(href)) link.classList.add('active');
});
