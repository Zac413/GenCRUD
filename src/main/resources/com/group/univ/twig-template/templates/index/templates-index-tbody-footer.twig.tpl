

        <td>
            <a href="{{ path('{{ENTITY_NAME_LOWER}}_edit', {id: {{ENTITY_NAME_LOWER}}.{{TWO_FIRST_LETTER}}Id}) }}" class="btn btn-warning btn-sm">✏️</a>
        </td>

        <td>
            <form action="{{ path('{{ENTITY_NAME_LOWER}}_delete', {id: {{ENTITY_NAME_LOWER}}.{{TWO_FIRST_LETTER}}Id}) }}" method="post" onsubmit="return confirm('Delete this {{ENTITY_NAME_LOWER}} ?');">
                <button class="btn btn-danger btn-sm">🗑️</button>
            </form>
        </td>
        </tr>
        {% endfor %}
    </tbody>
</table>
