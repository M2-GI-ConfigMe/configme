<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="orderLine">
        <h2 class="jh-entity-heading" data-cy="orderLineDetailsHeading">
          <span v-text="$t('configmeApp.orderLine.detail.title')">OrderLine</span> {{ orderLine.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('configmeApp.orderLine.config')">Config</span>
          </dt>
          <dd>
            <div v-if="orderLine.config">
              <router-link :to="{ name: 'ClientConfigView', params: { clientConfigId: orderLine.config.id } }">{{
                orderLine.config.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.orderLine.order')">Order</span>
          </dt>
          <dd>
            <div v-if="orderLine.order">
              <router-link :to="{ name: 'OrderView', params: { orderId: orderLine.order.id } }">{{ orderLine.order.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="orderLine.id"
          :to="{ name: 'OrderLineEdit', params: { orderLineId: orderLine.id } }"
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

<script lang="ts" src="./order-line-details.component.ts"></script>
