<template>
  <div>
    <h2 id="page-heading" data-cy="OrderLineHeading">
      <span v-text="$t('configmeApp.orderLine.home.title')" id="order-line-heading">Order Lines</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('configmeApp.orderLine.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'OrderLineCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-order-line"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('configmeApp.orderLine.home.createLabel')"> Create a new Order Line </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && orderLines && orderLines.length === 0">
      <span v-text="$t('configmeApp.orderLine.home.notFound')">No orderLines found</span>
    </div>
    <div class="table-responsive" v-if="orderLines && orderLines.length > 0">
      <table class="table table-striped" aria-describedby="orderLines">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('configmeApp.orderLine.config')">Config</span></th>
            <th scope="row"><span v-text="$t('configmeApp.orderLine.order')">Order</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="orderLine in orderLines" :key="orderLine.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'OrderLineView', params: { orderLineId: orderLine.id } }">{{ orderLine.id }}</router-link>
            </td>
            <td>
              <div v-if="orderLine.config">
                <router-link :to="{ name: 'ClientConfigView', params: { clientConfigId: orderLine.config.id } }">{{
                  orderLine.config.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="orderLine.order">
                <router-link :to="{ name: 'OrderView', params: { orderId: orderLine.order.id } }">{{ orderLine.order.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'OrderLineView', params: { orderLineId: orderLine.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'OrderLineEdit', params: { orderLineId: orderLine.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script lang="ts" src="./order-line.component.ts"></script>
