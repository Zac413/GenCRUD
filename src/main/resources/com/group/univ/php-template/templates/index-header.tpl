{% extends 'base.html.twig' %}

{% block title %}Liste des {{ENTITY_NAME}}{% endblock %}

{% block body %}
<h1>Liste des {{ENTITY_NAME}}</h1>

<table class="table">
    <thead>
    <tr>
        {{TABLE_HEADERS}}
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
