{% extends 'base.html.twig' %}

{% block body %}
    <h1>Liste des {{ENTITY_NAME}}s</h1>
    <table class="table">
        <thead>
            <tr>
                {% for field in FIELDS %}
                    <th>{{ field }}</th>
                {% endfor %}
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
        {% raw %}{% for item in list %}{% endraw %}
            <tr>
                {% for field in FIELDS %}
                    <td>{{ '{' }}{ item.{{ field }} }{ '}' }}</td>
                {% endfor %}
                <td>
                    <form method="post" action="{{ '{' }}{ path('{{ENTITY_NAME_LC}}_delete', {'id': item.id}) }{ '}' }}" style="display:inline;">
                        <button class="btn btn-danger btn-sm" type="submit" onclick="return confirm('Supprimer ?');">Supprimer</button>
                    </form>
                </td>
            </tr>
        {% raw %}{% endfor %}{% endraw %}
        </tbody>
    </table>
{% endblock %}