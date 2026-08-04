document.querySelectorAll('.toggle-password').forEach((btn) => {
    btn.addEventListener('click', () => {
        const input = btn.parentElement.querySelector('input');
        const showing = input.type === 'password';
        input.type = showing ? 'text' : 'password';
        btn.classList.toggle('is-active', showing);
        btn.setAttribute('aria-label', showing ? 'Hide password' : 'Show password');
    });
});