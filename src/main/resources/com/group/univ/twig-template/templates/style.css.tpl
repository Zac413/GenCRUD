@import url("https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css");

/* === PAGE BACKGROUND FIX === */
html, body {
background-color: #f8f9fa !important; /* fond gris clair */
color: #212529;
font-family: system-ui, -apple-system, "Segoe UI", Roboto, sans-serif;
margin: 0;
padding: 0;
height: 100%;
}

/* === CONTAINER POUR LE CONTENU === */
.container {
max-width: 960px;
margin: 2rem auto;
padding: 2rem;
background: #ffffff;
border-radius: 0.5rem;
box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

/* === FORMULAIRE CENTRÉ === */
form {
display: flex;
flex-direction: column;
align-items: center;
gap: 0.75rem;
}

/* === FORM FIELDS === */
input,
select,
textarea {
width: auto;
min-width: 200px;
padding: 0.375rem 0.75rem;
font-size: 1rem;
border: 1px solid #ced4da;
border-radius: 0.375rem;
background-color: #fff;
}

label {
display: block;
margin-bottom: 0.25rem;
margin-right: 0.25rem;
font-weight: 500;
text-align: left;
width: 100%;
}

/* === BOUTONS === */
button,
input[type="submit"],
input[type="button"] {
background-color: #0d6efd;
color: white;
padding: 0.4rem 0.75rem;
font-size: 0.9rem;
border: none;
border-radius: 0.375rem;
cursor: pointer;
white-space: nowrap;
margin-top: 0.5rem;
}

button:hover {
background-color: #0b5ed7;
}

/* === LIENS BOUTONS === */
a.btn {
background-color: #6c757d;
color: white;
padding: 0.4rem 0.75rem;
font-size: 0.9rem;
border-radius: 0.375rem;
text-decoration: none;
display: inline-block;
white-space: nowrap;
}

a.btn:hover {
background-color: #5c636a;
color: white;
}

/* TABLEAUX : largeur auto, centrés, compact */
table {
table-layout: auto;
width: auto;
max-width: 80vw;      /* Limite la largeur à 80% de la fenêtre */
min-width: 200px;     /* Largeur minimale pour la lisibilité */
margin-left: auto;
margin-right: auto;
border-collapse: collapse;
background: #fff;
box-shadow: 0 2px 8px rgba(0,0,0,0.03); /* Optionnel : effet de profondeur */
}

/* En-têtes et cellules centrés et compacts */
th, td {
padding: 0.4rem 0.6rem;
border: 1px solid #dee2e6;
text-align: center;
vertical-align: middle;
white-space: nowrap;
font-size: 0.95rem;
}

/* Boutons dans les tableaux : plats, centrés, sans fond */
td button,
td input[type="submit"],
td input[type="button"],
td .btn {
background: none;
color: #0d6efd;
border: none;
padding: 0.3rem 0.6rem;
font-size: 0.95rem;
border-radius: 0.375rem;
cursor: pointer;
margin: 0 auto;
display: block;
box-shadow: none;
transition: background 0.2s;
}

td button:hover,
td input[type="submit"]:hover,
td input[type="button"]:hover,
td .btn:hover {
background: #e9ecef;
color: #0b5ed7;
}

table thead {
background-color: #e9ecef;
font-weight: 600;
}

/* === TITRES === */
h1, h2, h3 {
font-weight: 600;
margin-bottom: 1.5rem;
text-align: center;
margin-top: 1.5rem;
}

.form-buttons {
display: flex;
justify-content: center;
gap: 0.75rem;
margin-top: 1rem;
}

.create-btn-container {
display: flex;
justify-content: center;
margin-bottom: 1.5rem;
gap: 0.75rem;
}