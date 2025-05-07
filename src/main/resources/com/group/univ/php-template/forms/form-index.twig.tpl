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
    {% for item in {{ENTITY_NAME_LC}} ~ 's' %}
    <tr>
        {{ TABLE_ROWS }}
        <td>
            <a href="{{ path('{{ ENTITY_NAME_LC }}_edit', {'id': item.id}) }}">✎ Modifier</a>
            <a href="{{ path('{{ ENTITY_NAME_LC }}_delete', {'id': item.id}) }}"
               onclick="return confirm('Supprimer ?')">🗑️</a>
        </td>
    </tr>
    {% endfor %}
    </tbody>
</table>

<a href="{{ path('{{ ENTITY_NAME_LC }}_new') }}" class="btn btn-primary">Créer un nouveau {{ENTITY_NAME_LC}}</a>
{% endblock %}
