<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="address">
        <h2 class="jh-entity-heading" data-cy="addressDetailsHeading">
          <span v-text="$t('configmeApp.address.detail.title')">Address</span> {{ address.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('configmeApp.address.zipCode')">Zip Code</span>
          </dt>
          <dd>
            <span>{{ address.zipCode }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.address.city')">City</span>
          </dt>
          <dd>
            <span>{{ address.city }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.address.streetNumber')">Street Number</span>
          </dt>
          <dd>
            <span>{{ address.streetNumber }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.address.streetName')">Street Name</span>
          </dt>
          <dd>
            <span>{{ address.streetName }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.address.complementary')">Complementary</span>
          </dt>
          <dd>
            <span>{{ address.complementary }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.address.firstName')">First Name</span>
          </dt>
          <dd>
            <span>{{ address.firstName }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.address.lastName')">Last Name</span>
          </dt>
          <dd>
            <span>{{ address.lastName }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="address.id" :to="{ name: 'AddressEdit', params: { addressId: address.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./address-details.component.ts"></script>
