{% extends 'base.html.twig' %}

{% block title %}Accueil{% endblock %}

{% block body %}
<h1>Accueil</h1>
<ul>
    {{ ENTITY_LINKS|raw }}
</ul>
{% endblock %}
