{% extends 'base.html.twig' %}

{% block title %}Créer un {{ENTITY_NAME_LC}}{% endblock %}

{% block body %}
<h1>Nouvel(le) {{ENTITY_NAME_LC}}</h1>

{{ form_start(form) }}
{{ form_widget(form) }}
<button class="btn">Enregistrer</button>
{{ form_end(form) }}
