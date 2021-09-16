<template>
  <v-row justify="center" class="pt-4" style="height: calc(100vh - 64px)" no-gutters>
    <div class="col-md-8 toastify-container">
      <h2 v-if="username" id="settings-title">
        <span>
          Informations de [<strong>{{ username }}</strong
          >]
        </span>
      </h2>

      <v-alert type="success" v-if="success">
        <strong>Informations sauvegardées !</strong>
      </v-alert>

      <v-form v-model="isValid" v-on:submit.prevent="save()">
        <!-- <v-form name="form" id="settings-form" role="form"  v-if="settingsAccount" novalidate> -->
        <div class="form-group">
          <v-text-field
            v-model="$v.settingsAccount.email.$model"
            :rules="rules.emailRules.concat(rules.requiredField)"
            type="email"
            label="E-mail"
            placeholder="template@email.com"
            hide-details="auto"
            outlined
            required
          >
          </v-text-field>
        </div>
        <div class="form-group">
          <v-row v-bind:no-gutters="true">
            <v-text-field
              v-model="$v.settingsAccount.firstName.$model"
              :rules="rules.firstNameRules.concat(rules.requiredField)"
              label="Prénom"
              placeholder="François"
              hide-details="auto"
              outlined
              required
            >
            </v-text-field>
            <v-text-field
              class="ml-4"
              v-model="$v.settingsAccount.lastName.$model"
              :rules="rules.lastNameRules.concat(rules.requiredField)"
              label="Nom"
              placeholder="Dupont"
              hide-details="auto"
              outlined
              required
            >
            </v-text-field>
          </v-row>
        </div>
        <div class="form-group">
          <v-row v-bind:no-gutters="true">
            <v-text-field
              v-model="$v.settingsAccount.address.streetNumber.$model"
              :rules="rules.streetNumberRules.concat(rules.requiredField)"
              label="N° de Rue"
              placeholder="5"
              hide-details="auto"
              outlined
              required
            >
            </v-text-field>
            <v-text-field
              class="ml-4"
              v-model="$v.settingsAccount.address.streetName.$model"
              :rules="rules.streetNameRules.concat(rules.requiredField)"
              label="Rue"
              placeholder="Rue de l'exemple"
              hide-details="auto"
              outlined
              required
            >
            </v-text-field>
          </v-row>
        </div>

        <div class="form-group">
          <v-row v-bind:no-gutters="true">
            <v-text-field
              v-model="$v.settingsAccount.address.city.$model"
              :rules="rules.cityRules.concat(rules.requiredField)"
              label="Ville"
              placeholder="Grenoble"
              hide-details="auto"
              outlined
              required
            >
            </v-text-field>
            <v-text-field
              class="ml-4"
              v-model="$v.settingsAccount.address.zipCode.$model"
              :rules="rules.zipCodeRules.concat(rules.requiredField)"
              label="Code postal"
              placeholder="38000"
              hide-details="auto"
              outlined
              required
            >
            </v-text-field>
          </v-row>
        </div>
        <!-- <div class="form-group" v-if="languages && Object.keys(languages).length > 1">
          <label for="langKey" v-text="$t('settings.form.language')">Language</label>
          <select class="form-control" id="langKey" name="langKey" v-model="settingsAccount.langKey" data-cy="langKey">
            <option v-for="(language, key) in languages" :value="key" :key="`lang-${key}`">{{ language.name }}</option>
          </select>
        </div> -->
        <button type="submit" :disabled="!isValid" class="btn btn-primary">Sauvegarder</button>
      </v-form>
    </div>
  </v-row>
</template>

<script lang="ts" src="./settings.component.ts"></script>
