from django.db import models

class Users(models.Model):
    id = models.IntegerField(primary_key=True)
    name = models.CharField(max_length=200)
    surname = models.CharField(max_length=200)
