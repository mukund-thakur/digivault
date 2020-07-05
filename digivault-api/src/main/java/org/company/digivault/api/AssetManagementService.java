package org.company.digivault.api;

import io.dropwizard.hibernate.UnitOfWork;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.company.digivault.config.DigiVaultConstants;
import org.company.digivault.request.CreateAssetRequest;
import org.company.digivault.request.UpdateAssetRequest;
import org.company.digivault.response.CreateAssetResponse;
import org.digivault.entity.Asset;
import org.digivault.entity.User;
import org.digivault.services.AssetMetaService;
import org.digivault.services.UserMetaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path(DigiVaultConstants.ASSET_BASE_API_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AssetManagementService {

  private static final Logger LOG = LoggerFactory.getLogger(AssetManagementService.class);

  private AssetMetaService assetMetaService;

  private UserMetaService userMetaService;

  public AssetManagementService(AssetMetaService assetMetaService, UserMetaService userMetaService) {
    this.assetMetaService = assetMetaService;
    this.userMetaService = userMetaService;
  }

  @POST
  @UnitOfWork
  public Response createAsset(CreateAssetRequest createAssetRequest) {
    LOG.debug("Create asset request received : {} ", createAssetRequest);
    validateCreateAssetReq(createAssetRequest);
    Asset asset = createAssetFromRequest(createAssetRequest);
    Asset createdAsset = assetMetaService.createAsset(asset);
    CreateAssetResponse createAssetResponse = generateCreateAssetResponse(createdAsset);

    return Response
            .ok(createAssetResponse)
            .build();
  }

  @GET
  @Path("/{assetId}")
  @UnitOfWork
  public Response getAssetById(@PathParam("assetId") Long assetId) {
    LOG.debug("Get asset request received for assetId : {} ", assetId);
    Asset asset = assetMetaService.getAssetById(assetId);

    if (asset == null) {
      throw new WebApplicationException("Asset not found ", Response.Status.BAD_REQUEST);
    }
    return Response
            .ok(asset)
            .build();
  }

  @PUT
  @Path("{assetId}")
  @UnitOfWork
  public Response updateAssetById(@PathParam("assetId") Long assetId,
                                  UpdateAssetRequest updateAssetRequest) {
    Asset assetFromDb = assetMetaService.getAssetById(assetId);
    if(assetFromDb == null) {
      throw new WebApplicationException("Asset not found ", Response.Status.BAD_REQUEST);
    }

    Asset updatedAsset = getUpdatedAsset(assetFromDb, updateAssetRequest);

    updatedAsset = assetMetaService.updateAsset(updatedAsset);

    return Response
            .ok(updatedAsset)
            .build();
  }

  private Asset getUpdatedAsset(Asset assetFromDb, UpdateAssetRequest updateAssetRequest) {
    assetFromDb.setType(updateAssetRequest.getType());
    assetFromDb.setServiceProvider(updateAssetRequest.getServiceProvider());
    assetFromDb.setAccountId(updateAssetRequest.getAccountId());
    assetFromDb.setAccountHolderName(updateAssetRequest.getAccountHolderName());
    assetFromDb.setAmountInvested(updateAssetRequest.getAmountInvested());
    assetFromDb.setNomineeReg(updateAssetRequest.isNomineeReg());
    assetFromDb.setNomineeName(updateAssetRequest.getNomineeName());
    assetFromDb.setNotes(updateAssetRequest.getNotes());
    assetFromDb.setDocumentId(updateAssetRequest.getDocumentId());
    return assetFromDb;
  }

  private Asset createAssetFromRequest(CreateAssetRequest createAssetRequest) {
    Asset asset = new Asset();
    asset.setUserId(createAssetRequest.getUserId());
    asset.setAccountHolderName(createAssetRequest.getAccountHolderName());
    asset.setAccountId(createAssetRequest.getAccountId());
    asset.setAmountInvested(createAssetRequest.getAmountInvested());
    asset.setNomineeReg(createAssetRequest.isNomineeReg());
    asset.setNomineeName(createAssetRequest.getNomineeName());
    asset.setType(createAssetRequest.getType());
    asset.setServiceProvider(createAssetRequest.getServiceProvider());
    asset.setDocumentId("Random_for_now");
    asset.setNotes(createAssetRequest.getNotes());
    return asset;
  }

  private CreateAssetResponse generateCreateAssetResponse(Asset createdAsset) {
     CreateAssetResponse createAssetResponse = new CreateAssetResponse();
     createAssetResponse.setAssetId(createdAsset.getId());
     return createAssetResponse;
  }


  private void validateCreateAssetReq(CreateAssetRequest createAssetRequest) {
    if (createAssetRequest.getUserId() == null) {
      throw new WebApplicationException("UserId not present in the body ", Response.Status.BAD_REQUEST);
    }
    User userById = userMetaService.getUserById(createAssetRequest.getUserId());

    if (userById == null) {
      throw new WebApplicationException("No user registered with userId " + createAssetRequest.getUserId(),
              Response.Status.BAD_REQUEST);
    }
  }
}
