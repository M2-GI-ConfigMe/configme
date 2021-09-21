<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="configmeApp.psu.home.createOrEditLabel"
          data-cy="PsuCreateUpdateHeading"
          v-text="$t('configmeApp.psu.home.createOrEditLabel')"
        >
          Create or edit a Psu
        </h2>
        <div>
          <div class="form-group" v-if="psu.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="psu.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.name')" for="computer-case-name">Nom</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="computer-case-name"
              data-cy="name"
              :class="{ valid: !$v.psu.name.$invalid, invalid: $v.psu.name.$invalid }"
              v-model="$v.psu.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.price')" for="computer-case-price">Prix</label>
            <input
              type="text"
              class="form-control"
              name="price"
              id="computer-case-price"
              data-cy="price"
              :class="{ valid: !$v.psu.price.$invalid, invalid: $v.psu.price.$invalid }"
              v-model="$v.psu.price.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.discount')" for="computer-case-discount">Réduction</label>
            <input
              type="text"
              class="form-control"
              name="discount"
              id="computer-case-discount"
              data-cy="discount"
              :class="{ valid: !$v.psu.discount.$invalid, invalid: $v.psu.discount.$invalid }"
              v-model="$v.psu.discount.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.stock')" for="computer-case-stock">Stock</label>
            <input
              type="text"
              class="form-control"
              name="stock"
              id="computer-case-stock"
              data-cy="stock"
              :class="{ valid: !$v.psu.stock.$invalid, invalid: $v.psu.stock.$invalid }"
              v-model="$v.psu.stock.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.img')" for="computer-case-img">Image</label>
            <input
              type="text"
              class="form-control"
              name="img"
              id="computer-case-img"
              data-cy="img"
              :class="{ valid: !$v.psu.img.$invalid, invalid: $v.psu.img.$invalid }"
              v-model="$v.psu.img.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.brand')" for="computer-case-brand">Marque</label>
            <input
              type="text"
              class="form-control"
              name="brand"
              id="computer-case-brand"
              data-cy="brand"
              :class="{ valid: !$v.psu.brand.$invalid, invalid: $v.psu.brand.$invalid }"
              v-model="$v.psu.brand.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.psu.power')" for="psu-power">Power</label>
            <input
              type="number"
              class="form-control"
              name="power"
              id="psu-power"
              data-cy="power"
              :class="{ valid: !$v.psu.power.$invalid, invalid: $v.psu.power.$invalid }"
              v-model.number="$v.psu.power.$model"
              required
            />
            <div v-if="$v.psu.power.$anyDirty && $v.psu.power.$invalid">
              <small class="form-text text-danger" v-if="!$v.psu.power.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.psu.power.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.psu.certification')" for="psu-certification">Certification</label>
            <input
              type="text"
              class="form-control"
              name="certification"
              id="psu-certification"
              data-cy="certification"
              :class="{ valid: !$v.psu.certification.$invalid, invalid: $v.psu.certification.$invalid }"
              v-model="$v.psu.certification.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.psu.modularity')" for="psu-modularity">Modularity</label>
            <select
              class="form-control"
              name="modularity"
              :class="{ valid: !$v.psu.modularity.$invalid, invalid: $v.psu.modularity.$invalid }"
              v-model="$v.psu.modularity.$model"
              id="psu-modularity"
              data-cy="modularity"
              required
            >
              <option value="NON_MODULAIRE" v-bind:label="$t('configmeApp.ModularityType.NON_MODULAIRE')">NON_MODULAIRE</option>
              <option value="SEMI_MODULAIRE" v-bind:label="$t('configmeApp.ModularityType.SEMI_MODULAIRE')">SEMI_MODULAIRE</option>
              <option value="MODULAIRE" v-bind:label="$t('configmeApp.ModularityType.MODULAIRE')">MODULAIRE</option>
            </select>
            <div v-if="$v.psu.modularity.$anyDirty && $v.psu.modularity.$invalid">
              <small class="form-text text-danger" v-if="!$v.psu.modularity.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.psu.nbSata')" for="psu-nbSata">Nb Sata</label>
            <input
              type="number"
              class="form-control"
              name="nbSata"
              id="psu-nbSata"
              data-cy="nbSata"
              :class="{ valid: !$v.psu.nbSata.$invalid, invalid: $v.psu.nbSata.$invalid }"
              v-model.number="$v.psu.nbSata.$model"
              required
            />
            <div v-if="$v.psu.nbSata.$anyDirty && $v.psu.nbSata.$invalid">
              <small class="form-text text-danger" v-if="!$v.psu.nbSata.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.psu.nbSata.min" v-text="$t('entity.validation.min', { min: 1 })">
                This field should be at least 1.
              </small>
              <small class="form-text text-danger" v-if="!$v.psu.nbSata.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.psu.nbPciE')" for="psu-nbPciE">Nb Pci E</label>
            <input
              type="number"
              class="form-control"
              name="nbPciE"
              id="psu-nbPciE"
              data-cy="nbPciE"
              :class="{ valid: !$v.psu.nbPciE.$invalid, invalid: $v.psu.nbPciE.$invalid }"
              v-model.number="$v.psu.nbPciE.$model"
              required
            />
            <div v-if="$v.psu.nbPciE.$anyDirty && $v.psu.nbPciE.$invalid">
              <small class="form-text text-danger" v-if="!$v.psu.nbPciE.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.psu.nbPciE.min" v-text="$t('entity.validation.min', { min: 1 })">
                This field should be at least 1.
              </small>
              <small class="form-text text-danger" v-if="!$v.psu.nbPciE.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.psu.outputs')" for="psu-outputs">Outputs</label>
            <input
              type="text"
              class="form-control"
              name="outputs"
              id="psu-outputs"
              data-cy="outputs"
              :class="{ valid: !$v.psu.outputs.$invalid, invalid: $v.psu.outputs.$invalid }"
              v-model="$v.psu.outputs.$model"
              required
            />
            <div v-if="$v.psu.outputs.$anyDirty && $v.psu.outputs.$invalid">
              <small class="form-text text-danger" v-if="!$v.psu.outputs.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.dimension.height')"> height</label>
            <input
              type="text"
              class="form-control"
              name="height"
              id="computer-case-height"
              data-cy="height"
              :class="{
                valid: !$v.psu.dimension.height.$invalid,
                invalid: $v.psu.dimension.height.$invalid,
              }"
              v-model="$v.psu.dimension.height.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.dimension.width')"> width</label>
            <input
              type="text"
              class="form-control"
              name="width"
              id="computer-case-width"
              data-cy="width"
              :class="{
                valid: !$v.psu.dimension.width.$invalid,
                invalid: $v.psu.dimension.width.$invalid,
              }"
              v-model="$v.psu.dimension.width.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.dimension.length')"> length</label>
            <input
              type="text"
              class="form-control"
              name="height"
              id="computer-case-length"
              data-cy="length"
              :class="{
                valid: !$v.psu.dimension.length.$invalid,
                invalid: $v.psu.dimension.length.$invalid,
              }"
              v-model="$v.psu.dimension.length.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.psu.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./psu-update.component.ts"></script>
