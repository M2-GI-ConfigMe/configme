<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="dimension">
        <h2 class="jh-entity-heading" data-cy="dimensionDetailsHeading">
          <span v-text="$t('configmeApp.dimension.detail.title')">Dimension</span> {{ dimension.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('configmeApp.dimension.height')">Height</span>
          </dt>
          <dd>
            <span>{{ dimension.height }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.dimension.width')">Width</span>
          </dt>
          <dd>
            <span>{{ dimension.width }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.dimension.length')">Length</span>
          </dt>
          <dd>
            <span>{{ dimension.length }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="dimension.id"
          :to="{ name: 'DimensionEdit', params: { dimensionId: dimension.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./dimension-details.component.ts"></script>
