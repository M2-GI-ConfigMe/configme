<template>
  <div class>
    <v-app-bar app class="px-15">
      <v-toolbar-title class="font-weight-bold" @click="$vuetify.goTo('#home', {})">
        <router-link to="/" tag="span" style="color: #2196f3; cursor: pointer; font-size: 1.5em">
          {{ appTitle }}
        </router-link>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <cart></cart>
      <v-btn color="primary" large elevation="0" v-on:click="openLogin()" v-if="!authenticated" class="ml-2"> Connexion </v-btn>
      <v-btn
        text
        v-if="authenticated"
        v-on:click="$router.push('/account/settings')"
        :color="$route.path == '/account/settings' ? 'primary' : ''"
        :class="$route.path == '/account/settings' ? 'font-weight-bold' : ''"
      >
        <v-icon class="mr-1">mdi-account</v-icon>Mon Compte
      </v-btn>
      <v-menu offset-y v-if="authenticated && hasAnyAuthority('ROLE_ADMIN')">
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            text
            v-bind="attrs"
            v-on="on"
            :color="hasItemActive(entitiesItems) ? 'primary' : ''"
            :class="hasItemActive(entitiesItems) ? 'font-weight-bold' : ''"
          >
            Entités
          </v-btn>
        </template>
        <v-list>
          <v-list-item
            v-for="(item, index) in entitiesItems"
            :key="index"
            @click="$router.push(item.path)"
            :class="item.path === $route.path ? 'primary text-white' : ''"
          >
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
      <v-toolbar-items>
        <v-menu offset-y v-if="authenticated && hasAnyAuthority('ROLE_ADMIN')">
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              text
              v-bind="attrs"
              v-on="on"
              :color="hasItemActive(adminItems) ? 'primary' : ''"
              :class="hasItemActive(adminItems) ? 'font-weight-bold' : ''"
            >
              Administration
            </v-btn>
          </template>
          <v-list>
            <v-list-item
              v-for="(item, index) in adminItems"
              :key="index"
              @click="$router.push(item.path)"
              :class="item.path === $route.path ? 'primary text-white' : ''"
            >
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-toolbar-items>
      <v-btn color="black" icon v-if="authenticated" v-on:click="logout()">
        <v-icon>mdi-power</v-icon>
      </v-btn>
    </v-app-bar>
  </div>
</template>

<script lang="ts" src="./navbar.component.ts"></script>
