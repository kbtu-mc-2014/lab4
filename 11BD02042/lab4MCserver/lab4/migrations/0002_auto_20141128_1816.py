# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('lab4', '0001_initial'),
    ]

    operations = [
        migrations.AlterField(
            model_name='users',
            name='id',
            field=models.IntegerField(serialize=False, primary_key=True),
        ),
    ]
