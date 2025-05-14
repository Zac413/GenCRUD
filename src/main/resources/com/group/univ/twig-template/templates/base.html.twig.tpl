<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>{% block title %}Welcome!{% endblock %}</title>

    <link rel="icon" href="data:image/svg+xml,
        <svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 128 128'>
            <text y='1.2em' font-size='96'>⚫️</text>
            <text y='1.3em' x='0.2em' font-size='76' fill='#fff'>sf</text>
        </svg>">

    <!-- ✅ Bootstrap 5 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">

    <!-- ✅ Ton fichier CSS pour styles automatiques -->
    <link rel="stylesheet" href="{{ asset('css/style.css') }}">

    {% block stylesheets %}{% endblock %}
    {% block javascripts %}
    {% block importmap %}{{ importmap('app') }}{% endblock %}
    {% endblock %}
</head>
<body>
    {% block body %}{% endblock %}
</body>
</html>